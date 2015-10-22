var app = angular.module('app', []);

function $_GET(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null)
        return unescape(r[2]);
    return null;
}

function $_ToJson(st) {
    return eval('(' + st + ')');
}



app.config(['$httpProvider', function ($httpProvider) {
    $httpProvider.defaults.useXDomain = true;
    delete $httpProvider.defaults.headers.common['X-Requested-With'];
}]);

app.filter('shortTime', function () {
    return function (inputString) {
        return inputString.split(' ')[0];
    }
});

app.service('HttpService', ['$http', function ($http) {
    this.post = function (style, method, param, callback) {
        param.style = style;
        param.method = method;
        $.ajax({
            cache: true,
            type: "POST",
            url: "http://service.1yingli.cn/yiyingliService/manage",
            data: $.toJSON(param),
            async: false,
            error: function (request) {
                // alert("Connection error");
            },
            success: function (data, textStatu) {
                callback($_ToJson(data));
            }
        });
    }
}]);

app.controller('IndexController', ['$scope', 'HttpService',

    //获取导师信息，并处理用于显示
    function ($scope, HttpService) {
        HttpService.post('user', 'getTeacherInfo', {"teacherId": $_GET("tid")}, function (data) {
            $scope.teacher = data;

            //导师tips处理
            {
                $scope.teacher.tips = $_ToJson($scope.teacher.tips);
                $scope.tips = {
                    1: {'text': '留学领航', val: 0},
                    2: {'text': '求职就业', val: 0},
                    4: {'text': '创业魅力', val: 0},
                    8: {'text': '校园生活', val: 0},
                    16: {'text': '猎奇分享', val: 0}
                };
                $.each($scope.teacher.tips, function (index, value) {
                    $scope.tips[value.id].val = 1;
                });
                $scope.tip_img_url = 'http://image.1yingli.cn/img/undefined_0';
            }

            $('#service').html($scope.teacher.serviceContent);

            //导师认证处理
            {
                $scope.check = {
                    1: {
                        img: "http://image.1yingli.cn/img/ident_01.png",
                        text: "实名认证",
                        status: $scope.teacher.checkIDCard
                    },
                    2: {
                        img: "http://image.1yingli.cn/img/ident_03.png",
                        text: "学位认证",
                        status: $scope.teacher.checkDegree
                    },
                    3: {
                        img: "http://image.1yingli.cn/img/ident_04.png",
                        text: "工作认证",
                        status: $scope.teacher.checkWork
                    },
                    4: {
                        img: "http://image.1yingli.cn/img/ident_07.png",
                        text: "手机认证",
                        status: $scope.teacher.checkPhone
                    },
                    5: {
                        img: "http://image.1yingli.cn/img/ident_08.png",
                        text: "邮箱认证",
                        status: $scope.teacher.checkEmail
                    }
                }
            }

            //导师经历处理
            {
                $scope.studyExprence = $_ToJson($scope.teacher.studyExperience);
                $scope.workExprence = $_ToJson($scope.teacher.workExperience);
            }

            //导师交流方式处理
            {
                $scope.talkWay = $_ToJson($scope.teacher.talkWay);
                $scope.talkWayUrl = {
                    10: "http://image.1yingli.cn/images/tel01.png",
                    11: "http://image.1yingli.cn/images/tel11.png",
                    20: "http://image.1yingli.cn/images/tel02.png",
                    22: "http://image.1yingli.cn/images/tel22.png",
                    30: "http://image.1yingli.cn/images/tel03.png",
                    33: "http://image.1yingli.cn/images/tel33.png"
                }
            }
            onReady($scope.teacher);
        });

        //评论相关
        {
            HttpService.post('teacher', 'getCommentCount', {teacherId: $_GET("tid")}, function (data) {
                $scope.commentInfo = data;
            });

            function changePage(page) {
                $scope.page = parseInt(page);
                HttpService.post('teacher', 'getCommentList', {'teacherId': $_GET("tid"), 'page': page},
                    function (data) {
                        $scope.comment = $_ToJson(data.data);
                    });
            }

            changePage('1');

            $scope.lastPage = function () {
                if ($scope.page > 1) {
                    changePage(($scope.page - 1) + "");
                }
            };

            $scope.nextPage = function () {
                if ($scope.page < parseInt($scope.commentInfo.page)) {
                    changePage(($scope.page + 1) + "");
                }
            };
        }


        {
            HttpService.post('function', 'getRecommendTeacherList', {teacherId: $_GET("tid")}, function (data) {
                $scope.recommend = $_ToJson(data.data);
                console.log($scope.recommend);
            });
        }


        $scope.loadMore = function (domst, foldst, morest, heightst) {
            var dom = $(domst);
            var fold = $(foldst);
            var more = $(morest);
            console.log(dom);
            if (dom.attr('more') != 1) {
                dom.css('height', 'auto');
                fold.css('display', 'none');
                dom.attr('more', 1);
                more.html('收起');
            } else {
                dom.css('height', heightst);
                fold.css('display', 'block');
                dom.attr('more', 0);
                more.html('显示更多');
            }
        };

        //为了产生星星
        $scope.getNumber = function (num) {
            num = parseInt(num);
            return new Array(num);
        }
    }
]);