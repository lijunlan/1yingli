app.service('HttpService', ['$http', '$rootScope', function ($http,$rootScope) {
    this.post = function (style, method, param, callback) {
        param.style = style;
        param.method = method;
        $.ajax({
            cache: true,
            type: "POST",
            url: "http://test.1yingli.cn/yiyingliService/manage",
            data: $.toJSON(param),
            error: function (request) {
                // alert("Connection error");
            },
            success: function (data, textStatu) {
                if($_ToJson(data).state == "error") {
                    alert($_ToJson(data).msg);
                    return;
                }
                callback($_ToJson(data));
                $rootScope.$digest();
            }
        });
    }
}]);