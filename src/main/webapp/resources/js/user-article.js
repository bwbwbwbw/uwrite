var currentId;

$('.role-delete').click(function () {
    currentId = $(this).closest('tr').attr('data');
    $('.modal').modal('show');
});

$('.role-delete-confirm').click(function() {
    // send ajax
    $.ajax({
        url: '/article/id/' + currentId,
        type: 'DELETE'
    }).done(function (data) {
        console.log(data);
        window.location.reload();
    });
});

