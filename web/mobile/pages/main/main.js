app.controller('MainController', ['$scope', 'HttpService', '$routeParams', '$location', '$rootScope',
    function ($scope, HttpService, $routeParams, $location) {

        //载入页面后保证滚动到头部
        $('body,html').scrollTop(0);


        //获取导师信息，并处理用于显示
        HttpService.post('user', 'getTeacherInfo', {"teacherId": $routeParams.tid}, function (data) {
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
            if ($routeParams.callback == 1) {
                $scope.date();
                $('#step1').css('display', 'none');
                $('#step5-1').css('display', 'block');
            }
        });

        //评论
        {
            HttpService.post('teacher', 'getCommentCount', {teacherId: $routeParams.tid}, function (data) {
                $scope.commentInfo = data;
            });

            function changePage(page) {
                $scope.page = parseInt(page);
                HttpService.post('teacher', 'getCommentList', {'teacherId': $routeParams.tid, 'page': page},
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

            //跳转
            $scope.changeTeache = function (teacherId) {
                $location.path('/main/' + teacherId);
            };
        }

        //相关话题
        {
            HttpService.post('function', 'getRecommendTeacherList', {teacherId: $routeParams.tid}, function (data) {
                $scope.recommend = $_ToJson(data.data);
            });
        }

        //显示更多
        {
            $scope.loadMore = function (domst, foldst, morest, heightst) {
                var dom = $(domst);
                var fold = $(foldst);
                var more = $(morest);
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
        }

        $scope.params = {
            question: "",
            userIntroduce: "",
            checkNo: "",
            teacherId: $routeParams.tid,
            selectTime: "",
            name: "",
            email: "",
            contact: ""
        };
        $scope.voucher = "";


        //预约流程控制
        {
            $scope.date = function () {
                //document.getElementById('iframe').addEventListener('touchmove', remove, false);
                $('#iframe').css('display', 'block');
                $('.blackBg').css('display', 'block');
                $('body,html').scrollTop(0);
                if ($scope.orderId != undefined) {
                    $scope.toPay();
                    return;
                }
                $('#iframe .box').css('display', 'none');
                $('#step1').css('display', 'block');
            };


            $scope.closeFrame = function () {
                //document.getElementById('iframe').removeEventListener('touchmove', remove, false);
                $('#iframe').css('display', 'none');
                $('.blackBg').css('display', 'none');
            };

            $scope.nextStep = function () {
                if ($scope.params.name == "") {
                    alert("请输入姓名");
                    return;
                }
                if ($scope.params.email == "") {
                    alert("请输入邮箱");
                    return;
                }
                if ($scope.params.contact == "") {
                    alert("请输入微信号");
                    return;
                }
                if ($scope.params.checkNO == "") {
                    alert("请输入验证码");
                    return;
                }
                $('#step1').css('display', 'none');
                $('#step2').css('display', 'block');
            };

            $scope.confirmOrder = function () {
                if ($scope.params.question == "") {
                    alert("请输入方便的时间");
                    return;
                }
                if ($scope.params.userIntroduce == "") {
                    alert("请输入自我介绍");
                    return;
                }
                if ($scope.params.selectTime == "") {
                    alert("请输入方便的时间");
                    return;
                }
                if ($scope.voucher != "") {
                    $scope.params.voucher = $scope.voucher
                }
                HttpService.post("order", "createOrderWithoutLogin", $scope.params, function (data) {
                    $scope.uid = data.uid;
                    $scope.orderId = data.orderId;
                    $scope.callback = 'http://' + window.location.host + window.location.pathname
                        + "#/main/" + $routeParams.tid + "/1";
                    $('#step2').css('display', 'none');
                    $('#step3').css('display', 'block');
                });
            };

            $scope.payType = 0;
            $scope.pay = function () {
                var payUrls = ["http://test.1yingli.cn/yiyingliService/Alipay", "http://test.1yingli.cn/yiyingliService/Checkout"];
                $('#step4 form').attr('action', payUrls[$scope.payType]);
                $('#step3').css('display', 'none');
                $('#step4').css('display', 'block');
            };

            $scope.toPay = function () {
                $('#iframe .box').css('display', 'none');
                $('#step3').css('display', 'block');
            };

            $scope.refusePay = function () {
                $('#step3').css('display', 'none');
                $('#step5-2').css('display', 'block');
            }

        }

        //获取验证码
        {
            function getCheckNOFun() {
                if ($scope.params.email == "") {
                    alert("请输入邮箱");
                    return;
                }
                HttpService.post('function', 'getCheckNo', {'username': $scope.params.email}, function (data) {
                    console.log(data);
                });

                $scope.getCheckNO = null;
                $scope.countdown = 60;
                $('#getCheckNO').addClass('disable');
                var myTime = setInterval(function () {
                    $scope.countdown--;
                    $scope.$digest();
                }, 1000);

                setTimeout(function () {
                    clearInterval(myTime);
                    $scope.countdown = "获取验证码";
                    $scope.$digest();
                    $scope.getCheckNO = getCheckNOFun;
                    $('#getCheckNO').removeClass('disable');
                }, 61000);
            }

            $scope.countdown = "获取验证码";
            $scope.getCheckNO = getCheckNOFun;
        }


        //为了产生星星
        $scope.getNumber = function (num) {
            num = parseInt(num);
            return new Array(num);
        }
    }
]);