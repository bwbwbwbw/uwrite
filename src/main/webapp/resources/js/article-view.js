$(document).ready(function() {

	$('.role-reply').click(function() {
		// send ajax
		$.ajax({
            url: '/comments',
            type: 'POST',
            data: {
            	markdown: $('.role-reply-body').val(),
            	articleId: 1
            }
		}).done(function(data) {

			$('#comment').append('<div class="comment">\
      <a class="avatar">\
        <img src="/images/avatar/small/matt.jpg">\
      </a>\
      <div class="content">\
        <a class="author">Matt</a>\
        <div class="metadata">\
          <span class="date">Today at 5:42PM</span>\
        </div>\
        <div class="text">\
          How artistic!\
        </div>\
        <div class="actions">\
          <a class="reply">Reply</a>\
        </div>\
      </div>\
    </div>');

		});
	});

});