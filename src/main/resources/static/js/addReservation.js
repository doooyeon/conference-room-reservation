function initView() {
    $('#date-picker').datepicker({
        format: 'yyyy-mm-dd',
        startDate: new Date(),
        todayHighlight: true,
        autoclose: true
    }).datepicker('update', new Date());
    $('#recursion-section').hide();
}

function initEvent() {
    $('#recursion-checkbox').on('click', recursionCheckEventHandler);
    $('#add-reservation-form').on('submit', function () {
        addReservation();
        return false;
    });
}

function recursionCheckEventHandler() {
    if ($('input:checkbox[id="recursion-checkbox"]').is(":checked")) {
        $('#recursion-section').show();
    } else {
        $('#recursion-section').hide();
    }
}

function checkTimeRange() {
    const startTime = $('#start-time').val();
    let startSplit = startTime.split(':');
    const startHour = parseInt(startSplit[0]);
    const startMn = parseInt(startSplit[1]);

    const endTime = $('#end-time').val();
    let endSplit = endTime.split(':');
    const endHour = parseInt(endSplit[0]);
    const endMn = parseInt(endSplit[1]);

    if (!(startMn === 0 || startMn === 30) || !(endMn === 0 || endMn === 30)) {
        $('#time-validation').html('30분 단위로 입력해주세요.');
        return false;
    }

    if (startHour < 9 || startHour > 20 || endHour < 9 || endHour > 20 || endHour === 20 && endMn !== 0) {
        $('#time-validation').html('09:00~20:00 시간을 입력해주세요.');
        return false;
    }

    if (!(startHour < endHour || startHour === endHour && startMn < endMn)) {
        $('#time-validation').html('종료 시간은 시작 시간 이후이어야 합니다.');
        return false;
    }

    if (startHour === 9) $('#start-time').val('0' + startTime);
    if (endHour === 9) $('#end-time').val('0' + endTime);

    return true;
}

function addReservation() {
    if (!checkTimeRange()) {
        return;
    }
    $('#time-validation').html('');

    let numOfRecursion = 1;
    if ($('input:checkbox[id="recursion-check"]').is(":checked")) {
        numOfRecursion = $('#num-of-recursion').val();
    }

    fetchManager({
        url: '/reservations',
        method: 'POST',
        headers: {'content-type': 'application/json'},
        body: JSON.stringify({
            roomName: $('#room-name').val(),
            reservedName: $('#reserved-name').val(),
            date: $('#date').val(),
            startTime: $('#start-time').val(),
            endTime: $('#end-time').val(),
            numOfRecursion: numOfRecursion
        }),
        onSuccess: addReservationSuccessCallback,
        onFailure: addReservationFailCallback
    })
}

function addReservationSuccessCallback() {
    document.location.href = '/viewReservation';
}

function addReservationFailCallback(response) {
    const status = response.status;
    response.json().then((response) => {
        if (status === 403) {
            // 이미 예약된 경우
            $('#time-validation').html(response.message);
        } else if (status === 400) {
            // 입력 유효성 오류
            response.errors.forEach((error) => {
            });
        }
    });
}

$(document).ready(function () {
    initView();
    initEvent();
});
