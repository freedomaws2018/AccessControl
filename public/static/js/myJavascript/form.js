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

  $.fn.form_verification = function(){
    $(".verification_msg").remove();
    var objects = $(this[0]).find("input,select");
    $.each(objects, function(index,obj){

      if( $(obj).is("[FDC-Verification]")){
        console.log(index, obj);
      }

    });
    return false;
  }

  $.fn.toJson = function(){
    var obj = {};
    var inputs = $(this).find("input[type!='radio'][type!='checkbox'][type!='file']");
    var radios = $(this).find("input[type='radio']:checked");
    var checkboxs = $(this).find("input[type='checkbox']:checked");
    var selects = $(this).find("select");// .find(":selected")

    $.each(inputs, function(index,data){
      if( data.name){
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
        if( $(data).attr("JsonValue")){
          var jd = JSON.parse(data.value);
          $.each(jd, function(key,value){
            obj[data.name + "[" + index + "]." + key] = value;
          });
        } else{
          obj[data.name + "[" + index + "]"] = data.value;
        }
      }
    });

    $.each(selects, function(index,data){
      // 單選
      if( data.name && $(data).find(":selected").length == 1){
        obj[data.name] = $(data).find(":selected").val();
      }
      // 多選
      else if( data.name && $(data).find(":selected").length > 1){
        $.each($(data).find(":selected"), function(index,data2){
          obj[data.name + "[" + index + "]"] = data2.value;
        });
      }
    });

    return obj;
  };

})(jQuery);