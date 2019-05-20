dateTimePicke = function(eveId,begId,endId){
  var startDate = $("#" + begId).val() ? $("#" + begId).val() : moment();
  var endDate = $("#" + endId).val() ? $("#" + endId).val() : moment();
//  console.log(startDate, endDate);
  $('#' + eveId).daterangepicker({
    "startDate" : startDate,
    "endDate" : endDate,
    "timePicker" : true,
    "showDropdowns" : true,
    "timePicker24Hour" : true,
    "alwaysShowCalendars" : true,
    "timePickerIncrement" : 10,
    "opens" : "left",
    "minYear" : 2018,
    "maxYear" : 2099,
    "locale" : {
      "format" : "YYYY-MM-DD HH:mm",
      "applyLabel" : "確定",
      "cancelLabel" : "取消",
      "customRangeLabel" : "自定義",
      "daysOfWeek" : [ "日", "一", "二", "三", "四", "五", "六" ],
      "monthNames" : [ "一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月" ],
      "firstDay" : 7
    },
    ranges : {
      '今天' : [ moment().startOf('days'), moment().add(1, 'days').startOf('days') ],
      '一周' : [ moment().startOf('days'), moment().add(1, 'week').startOf('days') ],
      '一月' : [ moment().startOf('days'), moment().add(1, 'month').startOf('days') ],
      '一年' : [ moment().startOf('days'), moment().add(1, 'year').startOf('days') ],
      '終身' : [ moment().startOf('days'), moment("2099-12-31T23:59") ]
    }
  }, function(beg,end,label){
    var begDt = beg.format('YYYY-MM-DD HH:mm');
    var endDt = end.format('YYYY-MM-DD HH:mm');
    $("#" + begId).val(begDt);
    $("#" + endId).val(endDt);
  });
}