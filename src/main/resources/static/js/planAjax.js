function planSave() {
    var param = {
        'title': $("#planTitle").val(),
        'context': $("#planContext").val(),
    }

    $.ajax({
        url: '/plan',
        type: 'POST',
        data: param,
    }).done(function () {
        alert("계획등록 완료");
        window.location.reload();
    }).fail(function () {
            alert("실패");
        }
    );
}