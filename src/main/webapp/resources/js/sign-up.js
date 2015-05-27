$(document).ready(function ()
{

  setTimeout(function () {
    $('.uwrite-animation.hide').each(function (i) {
      var self = $(this);
      setTimeout(function () {
        self.removeClass('hide');
      }, i * 100);
    });
  }, 500);

  var validationRules = 
  {
    email: 
    {
      identifier  : 'email',
      rules: 
      [
        {
          type   : 'empty',
          prompt : 'Please enter an e-mail'

        },
        {
          type   : 'email',
          prompt : 'Please enter a valid e-mail'
        }
      ]
    },
    nickname:
    {
      identifier:'nickname',
      rules:
      [
        {
          type:'empty',
          prompt:'Please pick up a nick name'
        }
      ]
    },
    password: 
    {
    identifier: 'password',
    rules: 
      [
        {
           type: 'empty',
           prompt: 'Please enter a password'
        },
        {
           type: 'length[3]',
           prompt: 'Password needs to be at least 3 characters long'
        }
      ]
    },       
    comfirmPassword:
    {
    identifier: 'confirmPassword',
    rules: 
      [
        {
          type: 'match[password]',
          prompt: 'Password don\'t match'
        }
      ]
    },
    isAgree:
    {
      identifier:'isAgree',
      rules:
      [
        {
        type:'checked',
        prompt: 'Your must agree to the terms and conditions'
        }
      ]
    }
  };

  $('.ui.dropdown')
    .dropdown({
      on: 'hover'
    })
  ;

  $('.ui.form')
    .form(validationRules, {
      on: 'blur',
      inline: 'true',
      transition: 'fade up',
      duration: 300,
      onSuccess: function () {
        $(this).addClass('loading');
        var form = $(this).form('get values');
        $.ajax({
          url: '/signup',
          type: 'POST',
          data: form
        }).done(function (data) {
          console.log(data);
        });
        return false;
      }
    });
  ;
  
  $('.ui.checkbox')
    .checkbox()
  ;
});
