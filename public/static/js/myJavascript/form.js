(function($){
  $.fn.serializeObject = function(){
    var o = {};
    var a = this.serializeArray();
    $.each(a, function(){
      if( o[this.name]){
        if( !o[this.name].push){
          o[this.name] = [ o[this.name] ];
        }
        o[this.name].push(this.value || '');
      } else{
        o[this.name] = this.value || '';
      }
    });
    return o;
  };

  $.fn.verification = function(){
    $(".input_error_msg").remove();
    var inputs = $(this).find("input[type!='radio']");
    var radios = $(this).find("input[type='radio']:checked");
    var selects = $(this).find("select");

    return true;
  }

  $.fn.toJson = function(){
    var obj = {};
    var inputs = $(this).find("input[type!='radio'][type!='checkbox'][type!='file']");
    var radios = $(this).find("input[type='radio']:checked");
    var checkboxs = $(this).find("input[type='checkbox']:checked");
    var selects = $(this).find("select");// .find(":selected")
    
    $.each(inputs, function(index,data){
      if( data.name ){
        obj[data.name] = data.value;
      }
    });
    
    $.each(radios, function(index,data){
      if( data.name){
        obj[data.name] = data.value;
      }
    });

    $.each(checkboxs, function(index,data){
      if( data.name){
        obj[data.name + "[" + index + "]"] = data.value;
      }
    });

    $.each(selects, function(index,data){
      if( data.name){
        obj[data.name] = $(data).find(":selected").val();
      }
    });

    return obj;
  };

})(jQuery);