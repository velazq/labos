$(function () {

    // var availabilityURL = "/api/availability";
    // var timetableURL = "/api/timetable";
    var availabilityURL = "http://localhost:4567/api/availability";
    var timetableURL = "http://localhost:4567/api/timetable";

    var allLabNumbers = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11];

    var timetable = "";

    $.getJSON(timetableURL, function (data) { // Preload timetable
        timetable = data;
    });


    function showCurrentAvailability(labNums) {
        var lNums = labNums;
        if (labNums === undefined || labNums === null) {
            lNums = allLabNumbers;
        }
        $.getJSON(availabilityURL, function (data) {
            var txt = "";
            for (var i = 0; i < lNums.length; i++) {
                var labNum = lNums[i];
                var lab = data["lab" + labNum];
                txt += "<p>En el lab " + labNum + " hay " + lab.free.length + " puestos libres</p>"
            }
            $("#availability").html(txt);
        });
    }

    function hideCurrentAvailability() {
        $("#availability").html("");3
    }

    function showTimetableEntry(labNums, weekday, hour, minutes) {
        if (labNums === null) {
            labNums = allLabNumbers;
        }
        function refreshTimetable(data) {
            timetable = data;
            var txt = "";
            for (var i = 0; i < labNums.length; i++) {
                var labNum = labNums[i];
                var labTag = "lab" + labNum;
                if (labTag in data) {
                    var lab = data[labTag];
                    var wday = "" + weekday;
                    if (wday in lab) {
                        var dayClasses = lab[wday];
                        var time = "" + hour + ":" + minutes;
                        if (time in dayClasses) {
                            if (dayClasses[time] !== "") {
                                txt += "<p>En el laboratorio " + labNum + " hay " + dayClasses[time] + "</p>";
                            } else {
                                txt += "<p>En el laboratorio " + labNum + " no hay clase en esta franja horaria</p>";
                            }
                        } else {
                            txt += "<p>Esta hora está fuera del horario del laboratorio " + labNum + "</p>";
                        }
                    } else {
                        txt += "<p>No tenemos el horario del laboratorio " + labNum + " para el día de la semana indicado</p>";
                    }
                } else {
                    txt += "<p>No tenemos los horarios del laboratorio " + labNum + "</p>";
                }
            }
            $("#timetable").html(txt);
        }
        if (timetable === "") {
            $.getJSON(timetableURL, refreshTimetable);
        } else {
            refreshTimetable(timetable);
        }
    }

    function getDateInfo() {
        var d = new Date();
        return {
            weekday:     d.getDay(),
            hour:        "" + d.getHours(),
            minutes:     d.getMinutes() < 30 ? "00" : "30",
            realMinutes: "" + d.getMinutes()
        };
    }

    function showDateInfo(weekday, hour, minutes) {
        var timeSlice = "" + hour + ":" + minutes;
        var days = ["domingo", "lunes", "martes", "miércoles", "jueves", "viernes", "sábado"];
        /*  <p class="panel-title">Visualizando el horario del lunes en la franja de las 9:00
            <i><button onclick="ocultar()" type="button" class="close" aria-hidden="true">&times;</button></i>
            </p>*/

        var i = "<i><button onclick= \"ocultar()\" type=\"button\" class=\"close\" aria-hidden=\"true\">&times;</button></i>";    
        var p = "<p class=\"panel-title\"> Visualizando el horario del " + days[weekday] + " en la franja de las " + timeSlice + "\n" + i + "</p>";
        

        $("#dateInfo").html(p);
    }

    function hideDateInfo() {
        $("#dateInfo").html("");
    }

    function refresh() {
        var labNumIdx = $("#sel1 option:selected").index();
        var labNums = null; // Todos los laboratorios
        if (labNumIdx !== 0) {
            labNums = [labNumIdx];
        }
        var weekday = $("#sel2 option:selected").index()+1;
        var hourTxt = $("#sel3 option:selected").text();
        var hour = "" + parseInt(hourTxt);
        var minutes = $("#sel4 option:selected").text();
        showDateInfo(weekday, hour, minutes);
        showTimetableEntry(labNums, weekday, hour, minutes);
        hideCurrentAvailability();
    }

    $(".form-control").change(refresh);

    var d = getDateInfo();
    showDateInfo(d.weekday, d.hour, d.minutes);
    showCurrentAvailability();



});

