$(document).ready(function () {
  setTimeout(function () {
    $('.uwrite-animation.hide').addClass('ready').each(function (i) {
      var self = $(this);
      setTimeout(function () {
        self.removeClass('hide');
      }, i * 100);
    });
  }, 500);

  $('.ui.checkbox').checkbox();

  $('.ui.form').form({}, {
    onSuccess: function () {
      $(this).addClass('loading');
      var form = $(this).form('get values');
      $.ajax({
        url: '/authenticate',
        type: 'POST',
        data: form
      }).done(function (data) {
        $('.ui.form').removeClass('loading');
        if (data.success == true) {
          $('.uwrite-animation').addClass('hide-out');
          setTimeout(function () {
            window.location = '/';
          }, 500);
        } else {
          $('.modal').modal('show');
        }
      });
      return false;
    }
  });
});
