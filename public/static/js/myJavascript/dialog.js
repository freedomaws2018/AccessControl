showConfirmDialog = function(titleText,bodyText,okFunction){
  var dialog = $('#dialog_default');
  dialog.modal({
    backdrop : 'static',
    keyboard : true,
    focus : true,
    show : false
  });
  var title = $('#dialog_default #dialog_default_title');
  title.text(titleText);
  var body = $('#dialog_default #dialog_default_body');
  body.text(bodyText);
  var okBtn = $('#dialog_default #dialog_default_ok');
  okBtn.unbind("click");
  okBtn.click(okFunction);
  dialog.modal('show');
}