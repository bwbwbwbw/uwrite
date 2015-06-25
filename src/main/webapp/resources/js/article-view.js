$(document).ready(function() {

  $('.aspect-ratio-fill').each(function () {
    var placeholder = $(this).parent();
    var ratio = String(parseInt(placeholder.css('max-height')) / parseInt(placeholder.css('max-width')) * 100) + '%';
    $(this).css('padding-bottom', ratio);
  });

	$('.role-reply').click(function() {
		// send ajax
		$.ajax({
            url: '/article/comment',
            type: 'POST',
            data: {
            	markdown: $('.role-reply-body').val(),
            	articleId: $('.role-article-id').val(),

            }
		}).done(function(data) {
            var comment = $('.comment-template .comment').clone();
            comment.find('img').attr('src', data.user.avatarLink);
            comment.find('.author').text(data.user.nickname);
            comment.find('.text').text(data.html);
            comment.hide().appendTo('#comment').fadeIn(600);
            $('.role-reply-body').val('');
		});
	});

});