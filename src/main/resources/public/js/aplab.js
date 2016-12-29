$(function () {

    var availabilityURL = "/api/availability";
    var timetableURL = "/api/timetable";
    // var availabilityURL = "http://localhost:4567/api/availability";
    // var timetableURL = "http://localhost:4567/api/timetable";

    var timetable = "";

    function showCurrentAvailability(labNum) {
        $.getJSON(availabilityURL, function(data) {
            var lab = data["lab" + labNum];
            $("#availability").text("En el lab " + labNum + " hay " + lab.free.length + " asientos libres");
        });
    }

    function showTimetableEntry(labNum, dateInfo) {
        var weekday = dateInfo.weekday;
        var timeSlice = dateInfo.timeSlice;
        function refreshTimetable(data) {
            timetable = data;
            var labInfo = data["lab" + labNum]["" + weekday];
            var txt = "";
            if (timeSlice in labInfo) {
                txt = "En el lab " + labNum + " hay " + labInfo[timeSlice];
            } else {
                txt = "Ahora no hay clase";
            }
            $("#timetable").text(txt);
        }
        if (timetable === "") {
            $.getJSON(timetableURL, refreshTimetable);
        } else {
            refreshTimetable(timetable);
        }
    }

    function getDateInfo() {
        var d = new Date();
        var minutes = d.getMinutes() < 30 ? "00" : "30";
        return {
            weekday:    d.getDay(),
            timeSlice:  "" + d.getHours() + ":" + minutes
        }
    }

    function showDateInfo(dateInfo) {
        var weekday = dateInfo.weekday;
        var timeSlice = dateInfo.timeSlice;
        var days = ["domingo", "lunes", "martes", "miércoles", "jueves", "viernes", "sábado"];
        $("#dateInfo").text("Estamos a " + days[weekday] + " en la franja de las " + timeSlice);
    }

    var dateInfo = getDateInfo();
    var labNum = 1;
    showDateInfo(dateInfo)
    showCurrentAvailability(labNum);
    //showTimetableEntry(labNum, dateInfo);
    showTimetableEntry(labNum, {weekday: 1, timeSlice: "9:30"});

});