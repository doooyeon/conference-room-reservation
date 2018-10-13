function initView() {
    $('#date-picker').datepicker({
        format: 'yyyy-mm-dd',
        startDate: new Date(),
        todayHighlight: true,
        autoclose: true
    }).datepicker('update', new Date());
    createTable();
    viewReservation();
}

function initEvent() {
    $('#view-reservation-form').on('submit', function () {
        viewReservation();
        return false;
    });
    $(window).on('resize', updateView)
}

function createTable() {
    const roomName = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'];

    let table = '<thead><tr><th></th>';
    for (let i = 0; i < roomName.length; i++) {
        table += '<th id="room' + roomName[i] + '">회의실' + roomName[i] + '</th>';
    }
    table += '</tr></thead><tbody>';

    let startHour = 9;
    const min = ['00', '30'];
    while (startHour < 21) {
        for (let i = 0; i < 2; i++) {
            table += '<tr>';
            table += '<td>' + startHour + ':' + min[i] + '</td>';
            for (let j = 0; j < roomName.length; j++) {
                table += '<td></td>';
            }
            table += '</tr>';
            if (startHour === 20) break;
        }
        startHour++;
    }

    $('#timetable').append(table);
}

function viewReservation() {
    $('body')[0].scroll(0, 0);
    fetchManager({
        url: '/reservations/' + $('#date').val(),
        method: 'GET',
        headers: {'content-type': 'application/json'},
        onSuccess: viewReserveSuccessCallback,
        onFailure: viewReserveFailCallback
    });
}

function viewReserveSuccessCallback(reservations) {
    reservationList = reservations;
    $('#show-reservation').html('');
    $.each(reservations, function (i, e) {
        createReservationBox(e.id, e.roomName, e.reservedName, e.startTime, e.endTime);
    });
}

function viewReserveFailCallback() {
}

function updateView() {
    if (reservationList.length) {
        viewReserveSuccessCallback(reservationList);
    }
}

function createReservationBox(id, room, reservedName, startTime, endTime) {
    const thRect = $('#room' + room)[0].getBoundingClientRect();
    const tdRect = $('td')[0].getBoundingClientRect();
    const div = '<div class="reservations-area-parent">' +
        '<div class="reservations-area" id="reservation' + id + '">' + reservedName + '</div>' +
        '</div>';
    $('#show-reservation').append(div);

    let startTimeSplit = startTime.split(':');
    const startHour = parseInt(startTimeSplit[0]);
    const startMn = parseInt(startTimeSplit[1]);

    let endTimeSplit = endTime.split(':');
    const endHour = parseInt(endTimeSplit[0]);
    const endMn = parseInt(endTimeSplit[1]);

    $('#reservation' + id).css({
        "position": "absolute",
        "top": (tdRect.top + 1) + tdRect.height * getTopPosition(startHour, startMn),
        "left": thRect.left + 1,
        "height": tdRect.height * getHeightPosition(startHour, startMn, endHour, endMn) - 1,
        "width": thRect.width - 1,
        "backgroundColor": "pink"
    });
}

function getTopPosition(hour, mn) {
    const startHour = 9;
    let position = 2 * (hour - startHour);
    if (mn === '30') position++;

    return position;
}

function getHeightPosition(startHour, startMn, endHour, endMn) {
    const hourDiff = endHour - startHour;
    const mnDiff = endMn - startMn;

    let position = hourDiff * 2;

    if (mnDiff > 0) {
        position++;
    } else if (mnDiff < 0) {
        position--;
    }

    return position;
}

$(document).ready(function () {
    initView();
    initEvent();
    let reservationList = [];
});
