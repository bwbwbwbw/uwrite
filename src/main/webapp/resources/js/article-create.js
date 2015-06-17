$(document).ready(function() {

  var editor = new MediumEditor('.role-content-edit', {
    buttons: ['bold', 'italic', 'underline', 'anchor', 'header1', 'header2', 'quote', 'orderedlist', 'unorderedlist', 'justifyLeft', 'justifyCenter', 'justifyRight']
  });

  $('.role-content-edit').mediumInsert({
    editor: editor
  });

});