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

  $.fn.form_verification = function(){
    var error_obj = [];
    $(".verification_err_msg").remove();
    var objects = $(this[0]).find("input,select");
    $.each(objects, function(index,obj){

      if( $(obj).is("[FDCV]")){
        var fdcvs = $(obj).attr('FDCV').split(',');

        // 空值驗證
        if( fdcvs.includes("not-blank")){
          if( isNotBlank(obj)){
            var placeholder = $(obj).attr('placeholder');
            if( placeholder){
              error_obj.push({
                obj : obj,
                err_msg : placeholder
              });
            } else{
              error_obj.push({
                obj : obj,
                err_msg : '不得為空'
              });
            }
          }
        }

        // 數值驗證
        if( fdcvs.includes("is-numeric")){
          if( !isNumeric(obj)){
            error_obj.push({
              obj : obj,
              err_msg : '需為數字'
            });
          }
        }

        // 整數驗證
        if( fdcvs.includes("is-integer")){
          if( !isInteger(obj)){
            error_obj.push({
              obj : obj,
              err_msg : '需為整數'
            });
          }
        }

        // 正整數驗證
        if( fdcvs.includes("is-positive-integer")){
          if( !isPositiveInteger(obj)){
            error_obj.push({
              obj : obj,
              err_msg : '需為正整數'
            });
          }
        }

        // 負整數驗證
        if( fdcvs.includes("is-negative-integer")){
          if( !isNegativeInteger(obj)){
            error_obj.push({
              obj : obj,
              err_msg : '需為負整數'
            });
          }
        }

      }

    });

    if( error_obj.length > 0){
      $(error_obj[0]).focus();
      var error_msg_obj = {};
      $.each(error_obj, function(index,err_obj){
        var obj = err_obj.obj;
        var erm = err_obj.err_msg;
        if( error_msg_obj[obj.name]){
          error_msg_obj[obj.name].msg += (' , ' + erm);
        } else{
          error_msg_obj[obj.name] = { obj:obj, msg:erm };
        }
      });
      // console.log(error_msg_obj);
      $.each(error_msg_obj,function(key,value){
        $(value.obj).after("<small class='form-text verification_err_msg'>" + value.msg + "</small>");
      });
      return true;
    }

    return false;
  }

})(jQuery);

if(!String.prototype.trim){
  String.prototype.trim = function(){
    return this.replace(/^\s+|\s+$/g, '');
  }
}

/** 不得為空 * */
isNotBlank = function(object){
  var value = $(object).val();
  switch(typeof value){
    case 'string':
//      value = $(object).val().trim();
      if( value == null || value == undefined || value == '' ){
        return true;
      }
      return false;
    case 'object':
      if( value == null || value == undefined || value.length == 0){
        return true;
      }
      return false;
  }
}

/** 驗證為整數 * */
isInteger = function(object){
  var value = '' + $(object).val();
  var reg = new RegExp('^-?\\d+$');
  if( reg.test(value)){
    return true;
  }
  return false;
}

/** 驗證為正整數 * */
isPositiveInteger = function(object){
  var value = '' + $(object).val();
  var reg = new RegExp('^-?\\d+$');
  if( reg.test(value) && value >= 0){
    return true;
  }
  return false;
}

/** 驗證為負整數 * */
isNegativeInteger = function(object){
  var value = $(object).val();
  var reg = new RegExp('^-?\\d+$');
  if( reg.test(value) && value <= 0){
    return true;
  }
  return false;
}

/** 驗證為數字 * */
isNumeric = function(object){
  var value = $(object).val();
  if( $.isNumeric(value)){
    return true;
  }
  return false;
}