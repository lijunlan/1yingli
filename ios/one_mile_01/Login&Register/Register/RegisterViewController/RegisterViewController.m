//
//  RegisterViewController.m
//  one_mile_01
//
//  Created by 王雅蓉 on 15/8/21.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import "RegisterViewController.h"

@interface RegisterViewController ()

@property (nonatomic, strong) RegisterView *registerV;

@end

@implementation RegisterViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    self.registerV = [[RegisterView alloc]initWithFrame:[[UIScreen mainScreen]bounds]];
    _registerV.backgroundColor = [UIColor clearColor];
    [self.view addSubview:_registerV];
    
    _registerV.myDelegate = self;
    
    // Do any additional setup after loading the view.
}

//设置导航栏
-(void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    [[UIApplication sharedApplication] setStatusBarStyle:UIStatusBarStyleLightContent]; //info中的status置为NO
}

-(void)viewWillDisappear:(BOOL)animated
{
    [super viewWillDisappear:animated];
    
    [[UIApplication sharedApplication] setStatusBarStyle:UIStatusBarStyleDefault];
}

#pragma mark -- 设置当前页的statusBar的字体颜色 (info中的status置为YES)
-(UIStatusBarStyle)preferredStatusBarStyle
{
    return UIStatusBarStyleLightContent;
}

#pragma mark -- RegisterViewDelegate
-(void)backToLogin
{
    [self dismissViewControllerAnimated:YES completion:^{
        
    }];
}

-(void)registerOne:(NSString *)username withPwd:(NSString *)pass withCheckNo:(NSString *)check withNikName:(NSString *)nickname
{
    [self getRegisterData:username withPWD:pass withCheckNo:check withNickName:nickname];
}

#pragma mark -- 请求数据
-(void)getRegisterData:(NSString *)username withPWD:(NSString *)password withCheckNo:(NSString *)checkno withNickName:(NSString *)nickname
{
    NSString *urlStr = @"http://service.1yingli.cn/yiyingliService/manage";
    
    [AFNConnect AFNConnectWithUrl:urlStr withBodyData:[AFNConnect createDataForRegister:username withPwd:password withCheckNo:checkno withNickName:nickname] connectBlock:^(id data) {
        
        //NSDictionary *dic = (NSDictionary *)[NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingMutableContainers error:nil];
        NSDictionary *dic = (NSDictionary *)[data objectFromJSONData];
        NSLog(@"register = %@", dic);
        NSString *state = [dic objectForKey:@"state"];
        
        if (![state isEqualToString:@"success"]) {
            
            UIAlertView *failAV = [[UIAlertView alloc] initWithTitle:nil message:@"Registration failure" delegate:self cancelButtonTitle:@"query" otherButtonTitles:nil];
            [self.view addSubview:failAV];
            
            [failAV show];
        } else {
        
            [self dismissViewControllerAnimated:YES completion:^{
                
                [self.myDelegate sendPhoneOrEmail:self.registerV.iphoneTextFile.text];
            }];
        }
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
