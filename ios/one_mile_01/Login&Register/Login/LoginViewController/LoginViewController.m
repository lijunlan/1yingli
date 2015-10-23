//
//  LoginViewController.m
//  one_mile_01
//
//  Created by 王雅蓉 on 15/8/21.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import "LoginViewController.h"
#import "AFNConnect.h"
#import "WXApiRequestHandler.h"
#import "WXApiManager.h"
#import "MainTabViewController.h"

@interface LoginViewController ()<WXApiManagerDelegate>

@property (nonatomic, strong) LoginView *loginV;

@end

@implementation LoginViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    self.loginV = [[LoginView alloc] initWithFrame:[[UIScreen mainScreen] bounds]];
    //loginV.backgroundColor = [UIColor brownColor];
    [self.view addSubview:_loginV];

    _loginV.myDelegate = self;
    
    UIGestureRecognizer *resignKeyboard = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(backgroundTapped:)];
    [self.view addGestureRecognizer:resignKeyboard];
    
    //Wechat login
    [WXApiManager sharedManager].delegate = self;
    [WeiboSDK enableDebugMode:YES];
}


+(instancetype)shareLoginHandler
{
    static LoginViewController *logvc;
    
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        logvc = [[LoginViewController alloc] init];
    });
    
    return logvc;
}

-(void)weiboHandleURL:(NSURL *)url
{
    [WeiboSDK handleOpenURL:url delegate:self];
    
}

//撤销键盘
-(void)backgroundTapped:(UITapGestureRecognizer *)tap
{
    [self.view endEditing:YES];
}

#pragma mark -- viewWillAppear & viewWillDisappear
-(void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    
    [[UIApplication sharedApplication] setStatusBarStyle:UIStatusBarStyleLightContent];  //info中的status置为NO
}

-(void)viewWillDisappear:(BOOL)animated
{
    [super viewWillDisappear:animated];
    
    [[UIApplication sharedApplication] setStatusBarStyle:UIStatusBarStyleDefault];
    
}

#pragma mark -- 设置当前页的statusBar的字体颜色  (info中的status置为YES)
-(UIStatusBarStyle)preferredStatusBarStyle
{
    return UIStatusBarStyleLightContent;
}

#pragma mark -- 模态（协议方法）
-(void)toEmail
{
    [self dismissViewControllerAnimated:YES completion:^{
        
    }];
}

-(void)toLogin:(NSString *)username withPwd:(NSString *)password
{
    [self.view endEditing:YES];
    
    if (([self isValidateEmail:username] || [self isValidateMobile:username]) && [self isValidatePassword:password]) {
        
        [self getLoginData:username withPWD:password];
    }
}

//忘记密码
-(void)forgetPassword{
    
    forgetPasswordViewController *forgetPasswordVC = [[forgetPasswordViewController alloc] init];
    
    forgetPasswordVC.modalTransitionStyle = UIModalTransitionStyleCrossDissolve;
    
    [self presentViewController:forgetPasswordVC animated:YES completion:^{
    
    }];
}

//注册
-(void)toRegister
{
    RegisterViewController *registerVC = [[RegisterViewController alloc] init];
    registerVC.myDelegate = self;
    registerVC.modalTransitionStyle = UIModalTransitionStyleCrossDissolve;
    
    [self presentViewController:registerVC animated:YES completion:^{
    }];
}

#pragma mark -- 有效手机号和邮箱 密码
//邮箱验证
-(BOOL)isValidateEmail:(NSString *)email
{
    NSString *emailRegex = @"[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}";
    NSPredicate *emailTest = [NSPredicate predicateWithFormat:@"SELF MATCHES %@", emailRegex];
    
    return [emailTest evaluateWithObject:email];
}

//手机号验证
-(BOOL)isValidateMobile:(NSString *)mobile
{
    //手机号以13， 15，18开头，八个 \d 数字字符
    NSString *phoneRegex = @"^((13[0-9])|(15[^4,\\D])|(18[0,0-9]))\\d{8}$";
    NSPredicate *phoneTest = [NSPredicate predicateWithFormat:@"SELF MATCHES %@",phoneRegex];
    
    //    NSLog(@"phoneTest is %@",phoneTest);
    return [phoneTest evaluateWithObject:mobile];
}


//密码验证
-(BOOL)isValidatePassword:(NSString *)password
{
    NSString *passRegex = @"[A-Za-z0-9]{6,20}";
    NSPredicate *passText = [NSPredicate predicateWithFormat:@"SELF MATCHES %@", passRegex];
    
    return [passText evaluateWithObject:password];
}

#pragma mark -- 数据请求 登录
-(void)getLoginData:(NSString *)name withPWD:(NSString *)pass
{
    //[[NSUserDefaults standardUserDefaults] setObject:@"0" forKey:@"isLogin"];
    
    [AFNConnect AFNConnectWithUrl:HTTPREQUEST withBodyData:[AFNConnect createDataForLogin:name withPwd:pass] connectBlock:^(id data) {
        
        //NSLog(@"data %@", data);
        
        NSDictionary *userDic = (NSDictionary *)[NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingMutableContainers error:nil];
        
        //NSLog(@"dic %@",userDic);
        LoginModal *userLG = [[LoginModal alloc] init];
        
        for (NSString *key in userDic.allKeys) {
            
            if ([key isEqualToString:@"uid"]) {
                
                userLG.uid = [userDic objectForKey:@"uid"];
            } else if ([key isEqualToString:@"nickName"]) {
            
                userLG.nickName = [userDic objectForKey:@"nickName"];
            } else if ([key isEqualToString:@"iconUrl"]) {
                
                userLG.iconUrl = [userDic objectForKey:@"iconUrl"];
            } else if ([key isEqualToString:@"state"]) {
            
                userLG.state = [userDic objectForKey:@"state"];
            } else if ([key isEqualToString:@"teacherId"]) {
            
                userLG.teacherId = [userDic objectForKey:@"teacherId"];
            }
        }
        
        //NSLog(@"state = %@", userLG.state);
        
        if ([userLG.state isEqualToString:@"success"]) {
            
            [[NSUserDefaults standardUserDefaults] setObject:@"1" forKey:@"isLogin"];
            
            if (userLG.teacherId.length == 0) {
                
                [[NSUserDefaults standardUserDefaults] setObject:[NSDictionary dictionaryWithObjectsAndKeys:userLG.uid , @"uid", name, @"number", userLG.nickName, @"nickName", userLG.iconUrl, @"iconUrl", userLG.teacherId, @"teacherID", nil] forKey:@"userInfo"];
            } else {
            
                [[NSUserDefaults standardUserDefaults] setObject:[NSDictionary dictionaryWithObjectsAndKeys:userLG.uid , @"uid", name, @"number", userLG.nickName, @"nickName", userLG.teacherId, @"teacherID", userLG.iconUrl, @"iconUrl", nil] forKey:@"userInfo"];
            }
            //NSLog(@"userInfoDic = %@", [[NSUserDefaults standardUserDefaults] objectForKey:@"userInfo"]);
            
            if (userLG.teacherId.length == 0) {
                
                [TagForClient shareTagDataHandle].isTeacher = NO;
            } else {
            
                [TagForClient shareTagDataHandle].isTeacher = YES;
            }
            
            [self dismissViewControllerAnimated:YES completion:^{
                
                
            }];
            
        } else {
        
            self.loginV.idTF.text = @"用户手机号或邮箱";
            self.loginV.passTF.text = @"用户密码";
            UIAlertView *alert = [[UIAlertView alloc] initWithTitle:nil message:@"用户名或密码输入有误" delegate:self cancelButtonTitle:@"确定" otherButtonTitles:nil];
            
            [alert show];
        }
        
        [self.myColDelegate transitionViewToCollection:userLG];
        [self.myOrderDelegate transitionViewToOrder:userLG];
        [self.myEmailDelegate transitionVIewToEmail:userLG];
    }];
}

#pragma mark -- registerControllerDelegate
-(void)sendPhoneOrEmail:(NSString *)phoneNumOrEmail
{
    self.loginV.idTF.text = phoneNumOrEmail;
}

#pragma mark -- thirdpartLogin Wechat
-(void)thirdpartToWechatLogin
{
    [WXApiRequestHandler sendAuthRequestScope:@"snsapi_message,snsapi_userinfo,snsapi_friend,snsapi_contact"
                                        State:@"xxx"
                                       OpenID:@"0c806938e2413ce73eef92cc3"
                             InViewController:self];
}

-(void)thirdpartToWeiboLogin{
    
    [TagForClient shareTagDataHandle].weiboLoginV = _loginV;
    
    WBAuthorizeRequest *request = [WBAuthorizeRequest request];
    request.redirectURI = @"http://www.1yingli.cn";
    request.scope = @"all";
    request.userInfo = @{@"SSO_From": @"LoginViewController",
                         @"Other_Info_1": [NSNumber numberWithInt:1],
                         @"Other_Info_2": @[@"obj1", @"obj2"],
                         @"Other_Info_3": @{@"key1": @"obj1", @"key2": @"obj2"}};
    [WeiboSDK sendRequest:request];
}

#pragma mark -- WXApiManagerDelegate
-(void)managerDidRecvAuthResponse:(SendAuthResp *)response
{
    [self thirdpartLoginHandler:response.code withUID:nil];
    
}

#pragma mark -- 微博delegate

- (void)didReceiveWeiboRequest:(WBBaseRequest *)request
{
    
}

- (void)didReceiveWeiboResponse:(WBBaseResponse *)response
{
    if ([response isKindOfClass:WBAuthorizeResponse.class])
    {
        NSString  *wbtoken = [(WBAuthorizeResponse *)response accessToken];
        NSString  *wbCurrentUserID = [(WBAuthorizeResponse *)response userID];
        
        [self thirdpartLoginToWeibo:wbtoken withUID:wbCurrentUserID];
    }
    
}

#pragma mark -- 三方登录
//微信
-(void)thirdpartLoginHandler:(NSString *)code withUID:(NSString *)uid
{
    if (code.length == 0 || uid.length == 0) {
        return;
    }
    
    [AFNConnect AFNConnectWithUrl:HTTPREQUEST withBodyData:[AFNConnect createDataForThirdWechatLogin:code] connectBlock:^(id data) {
        
        NSDictionary *dic = (NSDictionary *)[NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingMutableContainers error:nil];
        
        LoginModal *userLG = [[LoginModal alloc] init];
        
        for (NSString *key in dic.allKeys) {
            
            if ([key isEqualToString:@"uid"]) {
                
                userLG.uid = [dic objectForKey:@"uid"];
            } else if ([key isEqualToString:@"nickName"]) {
                
                userLG.nickName = [dic objectForKey:@"nickName"];
            } else if ([key isEqualToString:@"iconUrl"]) {
                
                userLG.iconUrl = [dic objectForKey:@"iconUrl"];
            } else if ([key isEqualToString:@"state"]) {
                
                userLG.state = [dic objectForKey:@"state"];
            } else if ([key isEqualToString:@"teacherId"]) {
                
                userLG.teacherId = [dic objectForKey:@"teacherId"];
            }
        }
        
        //NSLog(@"state = %@", userLG.state);
        
        if ([userLG.state isEqualToString:@"success"]) {
            
            [[NSUserDefaults standardUserDefaults] setObject:@"1" forKey:@"isLogin"];
            
            if (userLG.teacherId.length == 0) {
                
                [[NSUserDefaults standardUserDefaults] setObject:[NSDictionary dictionaryWithObjectsAndKeys:userLG.uid , @"uid", @"", @"number", userLG.nickName, @"nickName", userLG.iconUrl, @"iconUrl", userLG.teacherId, @"teacherID", nil] forKey:@"userInfo"];
            } else {
                
                [[NSUserDefaults standardUserDefaults] setObject:[NSDictionary dictionaryWithObjectsAndKeys:userLG.uid , @"uid", @"", @"number", userLG.nickName, @"nickName", userLG.teacherId, @"teacherID", userLG.iconUrl, @"iconUrl", nil] forKey:@"userInfo"];
            }
            //NSLog(@"userInfoDic = %@", [[NSUserDefaults standardUserDefaults] objectForKey:@"userInfo"]);
            
            if (userLG.teacherId.length == 0) {
                
                [TagForClient shareTagDataHandle].isTeacher = NO;
            } else {
                
                [TagForClient shareTagDataHandle].isTeacher = YES;
            }
            
            [self dismissViewControllerAnimated:YES completion:^{
                
            }];
        } else {
            
            UIAlertView *alert = [[UIAlertView alloc] initWithTitle:nil message:@"Logon failure : unknown user name or bad password" delegate:self cancelButtonTitle:@"query" otherButtonTitles:nil];
            [self.view addSubview:alert];
            
            [alert show];
        }
        self.loginV.idTF.text = @"用户手机号或邮箱";
        self.loginV.passTF.text = @"用户密码";
        
        [self.myColDelegate transitionViewToCollection:userLG];
        [self.myOrderDelegate transitionViewToOrder:userLG];
        [self.myEmailDelegate transitionVIewToEmail:userLG];
    }];
}

//微博
-(void)thirdpartLoginToWeibo:(NSString *)wbtoken withUID:(NSString *)uid{
    
    if (wbtoken.length == 0 || uid.length == 0) {
        return;
    }
    
    [AFNConnect AFNConnectWithUrl:HTTPREQUEST withBodyData:[AFNConnect createDataForThirdWeiboLogin:wbtoken withUID:uid] connectBlock:^(id data) {
    
        NSDictionary *dic = (NSDictionary *)[NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingMutableContainers error:nil];
    
        LoginModal *userLG = [[LoginModal alloc] init];
    
        for (NSString *key in dic.allKeys) {
        
            if ([key isEqualToString:@"uid"]) {
            
                userLG.uid = [dic objectForKey:@"uid"];
            } else if ([key isEqualToString:@"nickName"]) {
            
                userLG.nickName = [dic objectForKey:@"nickName"];
            } else if ([key isEqualToString:@"iconUrl"]) {
            
                userLG.iconUrl = [dic objectForKey:@"iconUrl"];
            } else if ([key isEqualToString:@"state"]) {
            
                userLG.state = [dic objectForKey:@"state"];
            } else if ([key isEqualToString:@"teacherId"]) {
                
                userLG.teacherId = [dic objectForKey:@"teacherId"];
            }
        }
        
        if ([userLG.state isEqualToString:@"success"]) {
        
            [[NSUserDefaults standardUserDefaults] setObject:@"1" forKey:@"isLogin"];
            
            if (userLG.teacherId.length == 0) {
                
                [[NSUserDefaults standardUserDefaults] setObject:[NSDictionary dictionaryWithObjectsAndKeys:userLG.uid , @"uid", @"", @"number", userLG.nickName, @"nickName", userLG.iconUrl, @"iconUrl", userLG.teacherId, @"teacherID", nil] forKey:@"userInfo"];
            } else {
                
                [[NSUserDefaults standardUserDefaults] setObject:[NSDictionary dictionaryWithObjectsAndKeys:userLG.uid , @"uid", @"", @"number", userLG.nickName, @"nickName", userLG.teacherId, @"teacherID", userLG.iconUrl, @"iconUrl", nil] forKey:@"userInfo"];
            }
            
            if (userLG.teacherId.length == 0) {
            
                [TagForClient shareTagDataHandle].isTeacher = NO;
            } else {
            
                [TagForClient shareTagDataHandle].isTeacher = YES;
            }
            
//            [self dismissViewControllerAnimated:YES completion:^{
//                NSLog(@"我要模态走");
//            }];
            [[TagForClient shareTagDataHandle].weiboLoginV backToMainTabVC];

        } else {
        
            UIAlertView *alert = [[UIAlertView alloc] initWithTitle:nil message:@"Login failure : unknown user name or bad password" delegate:self cancelButtonTitle:@"query" otherButtonTitles:nil];
        
            [alert show];
        }
        self.loginV.idTF.text = @"用户手机号或邮箱";
        self.loginV.passTF.text = @"用户密码";
    
        [self.myColDelegate transitionViewToCollection:userLG];
        [self.myOrderDelegate transitionViewToOrder:userLG];
        [self.myEmailDelegate transitionVIewToEmail:userLG];
        
    }];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
