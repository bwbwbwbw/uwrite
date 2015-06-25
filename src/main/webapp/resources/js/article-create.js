var topicId;

var isEditing = false;
var articleId = null;

if ($('.role-edit').length == 0) {
  // creating mode

  topicId = localStorage.getItem("create-topic-id") || 1;

  if (localStorage.getItem("create-topic-name")) {
    $('.role-topic').text(localStorage.getItem("create-topic-name"));
  }

  if (localStorage.getItem('create-html')) {
    $('.article-content').html(localStorage.getItem('create-html'));
  }
} else {
  // editing mode
  topicId = parseInt($('.role-edit-topic-id').val());
  isEditing = true;
  articleId = $('.role-edit-article-id').val();

  $('.graf--h2, .graf--h3').eq(0).attr('contenteditable', 'plaintext-only');

  if ($('.role-content-edit').length == 0) {
    $('.postArticle-content').addClass('role-content-edit');
  }
}


setInterval(function() {
  localStorage.setItem('create-html', $('.article-content').html());
}, 1000);


$(document).ready(function() {

  var editor = new MediumEditor('.role-content-edit', {
    buttons: ['bold', 'italic', 'underline', 'anchor', 'header1', 'header2', 'quote', 'orderedlist', 'unorderedlist', 'justifyLeft', 'justifyCenter', 'justifyRight']
  });

  $('.role-content-edit').mediumInsert({
    editor: editor
  });

  $(document).on('click', '.topic-item', function() {
    var topicName = $(this).find('.topic-name').text();
    topicId = parseInt($(this).attr('data-id'));
    localStorage.setItem("create-topic-name", topicName);
    localStorage.setItem("create-topic-id", topicId);
    $('.role-topic').text(topicName);
    $('.dialog-topics').modal('hide');
  });

  $('.role-select-topic').click(function () {
    $.get('/topics.json').done(function(data) {
      var $dialog = $('.dialog-topics').empty();
      var $content = $('<div class="content"></div>').appendTo($dialog);
      data.forEach(function(topic) {
        var item = $('<div class="topic-item"></div>').attr('data-id', topic.id);
        if (topic.id == topicId) {
          item.addClass('topic-selected');
        }
        $('<div class="topic-name"></div>').text(topic.name).appendTo(item);
        $('<div class="topic-desc"></div>').text(topic.description).appendTo(item);
        item.appendTo($content);
      });
      $dialog.modal('show');
    });
  });

  $('.role-publish').click(function() {
    var img = $('.article-content img');
    if (img.length > 0) {
      var coverImage = img.attr('src');
    } else {
      var coverImage = null;
    }

    var paragraphs = $('.article-content p');
    var pi = 0;
    var brief = [], briefLength = 0;
    while (briefLength < 200 && pi < paragraphs.length) {
      brief.push(paragraphs.eq(pi).text());
      briefLength += paragraphs.eq(pi).text().length;
      pi++;
    }
    if (briefLength > 400) {
      brief[brief.length - 1] = brief[brief.length - 1].substr(0, 500 - (briefLength -  brief[brief.length - 1].length)) + '...';
    }

    var node = $('.article-content').clone();
    node.find('[contenteditable]').removeAttr('contenteditable');
    node.find('.medium-insert-buttons').remove();

    var data = {
      title: $('.graf--h2, .graf--h3').eq(0).text(),
      html: node.html(),
      topicId: parseInt(topicId),
      brief: brief.filter(function(p) { return p.trim().length > 0; }).map(function(p) { return '<p>' + $('<div>').text(p).html() + '</p>'; }).join('')
    };
    if (coverImage !== null) {
      data.coverImage = coverImage;
    }

    var xhr;
    if (!isEditing) {
      xhr = $.ajax({
        url: '/article/create',
        method: 'POST',
        data: data
      });
    } else {
      xhr = $.ajax({
        url: '/article/id/' + articleId,
        method: 'POST',
        data: data
      });
    }

    xhr.done(function(data) {
      localStorage.removeItem('create-topic-name');
      localStorage.removeItem('create-topic-id');
      localStorage.removeItem('create-html');
      window.location.href = data.finalUrl;
    });

  });

});