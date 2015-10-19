//
//  WantTobeTutorViewController.m
//  one_mile_01
//
//  Created by 王雅蓉 on 15/8/24.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import "WantTobeTutorViewController.h"
#import "TobeTutorForInfoView.h"
#import "TobeTutorForEduView.h"
#import "TobeTutorForWorkView.h"
#import "TobeTutorForServiceView.h"

#define INFOVIEWHEIGHT 400

@interface WantTobeTutorViewController ()<UIAlertViewDelegate>

@property (nonatomic, strong) TobeTutorForInfoView *tobeInfoV;
@property (nonatomic, strong) TobeTutorForEduView *tobeEduV;
@property (nonatomic, strong) TobeTutorForWorkView *tobeWorkV;
@property (nonatomic, strong) TobeTutorForServiceView *tobeServV;

@end

@implementation WantTobeTutorViewController

-(instancetype)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    if (self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil]) {
        
    }
    
    return self;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    self.view.backgroundColor = [UIColor whiteColor];
    
    UILabel *titleL = [[UILabel alloc] initWithFrame:CGRectMake(WIDTH / 2.0 - 70, 30, 140, NAVIGATIONHEIGHT - 30)];
//    titleL.backgroundColor = [UIColor yellowColor];
    titleL.text = @"申请导师";
    titleL.textAlignment = NSTextAlignmentCenter;
    titleL.font = [UIFont fontWithName:@"TimesNewRomanPS-BoldMT" size:20.0f];
    titleL.textColor = [UIColor lightGrayColor];
    [self.view addSubview: titleL];
    
    UIButton *backBT = [UIButton buttonWithType:UIButtonTypeCustom];
    backBT.frame = CGRectMake(20, titleL.frame.origin.y + 8, PUSHANDPOPBUTTONSIZE, PUSHANDPOPBUTTONSIZE);
    [backBT setBackgroundImage:[UIImage imageNamed:@"pop_dark.png"] forState:UIControlStateNormal];
    [self.view addSubview:backBT];
    
    [backBT addTarget:self action:@selector(backToBe:) forControlEvents:UIControlEventTouchUpInside];

    [self setSubviews];
    
    UIGestureRecognizer *resignGR = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(resignKeyboardEvent:)];
    [self.view addGestureRecognizer:resignGR];
}

#pragma mark -- 单点手势
-(void)resignKeyboardEvent:(UITapGestureRecognizer *)tap
{
    [self.view endEditing:YES];
}

#pragma mark -- 模态button
-(void)backToBe:(UIButton *)button
{
    [self dismissViewControllerAnimated:YES completion:^{
        
    }];
}

#pragma mark -- 提交button
-(void)submitAction:(UIButton *)button
{
    UIAlertView *alertV = [[UIAlertView alloc] initWithTitle:@"提示" message:[NSString stringWithFormat:@"确认提交？\n联系方式：%@", _tobeInfoV.phoneForTobeTF.text] delegate:self cancelButtonTitle:@"取消" otherButtonTitles:@"确定", nil];
    
    [alertV show];
}

#pragma mark -- 提交数据请求
-(void)submitDataHandle:(NSString *)application
{
    [AFNConnect AFNConnectWithUrl:HTTPREQUEST withBodyData:[AFNConnect createDataForTobeTutor:[[[NSUserDefaults standardUserDefaults] objectForKey:@"userInfo"] objectForKey:@"uid"] withApplication:application] connectBlock:^(id data) {
        
        NSDictionary *dic = [NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingMutableContainers error:nil];
        
        if ([[dic objectForKey:@"state"] isEqualToString:@"success"]) {
            
            UIAlertView *alertV = [[UIAlertView alloc] initWithTitle:nil message:@"申请成功，我们尽快给您答复" delegate:self cancelButtonTitle:@"确定" otherButtonTitles:nil];
            [alertV show];
        } else {
        
            if ([[dic objectForKey:@"msg"] isEqualToString:@"uid is not existed"]) {
                
                [[NSUserDefaults standardUserDefaults] setObject:@"0" forKey:@"isLogin"];
                
                UIAlertView *alertV = [[UIAlertView alloc] initWithTitle:@"提示" message:@"登录超时，请重新登录" delegate:self cancelButtonTitle:@"取消" otherButtonTitles:@"登录", nil];
                [alertV show];
            }
        }
    }];
}

-(void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex
{
    if ([alertView.message isEqualToString:@"申请成功，我们尽快给您答复"]) {
        
        if (buttonIndex == 0) {
            
            [self dismissViewControllerAnimated:YES completion:^{
                
            }];
        }
    } else if ([alertView.message isEqualToString:@"登录超时，请重新登录"]) {
    
        if (buttonIndex == 0) {
            
            [self dismissViewControllerAnimated:YES completion:^{
                
            }];
        } else {
        
            LoginViewController *loginVC = [[LoginViewController alloc] init];
            loginVC.modalTransitionStyle = UIModalTransitionStyleFlipHorizontal;
            [self presentViewController:loginVC animated:YES completion:^{
                
            }];
        }
    } else {
    
        if (buttonIndex == 1) {
            
            NSString *dataStr = [NSString stringWithFormat:@"{\"service\":%@,\"workExperience\":%@,\"studyExperience\":%@,%@}", [_tobeServV saveServiceInfo], [_tobeWorkV saveWorkInfo], [_tobeEduV saveEduInfo], [_tobeInfoV saveInfo]];
            
            [self submitDataHandle:dataStr];
        }
    }
}

-(void)setSubviews
{
    self.toBeScrollView = [[UIScrollView alloc] initWithFrame:CGRectMake(0, NAVIGATIONHEIGHT, WIDTH, HEIGHT - NAVIGATIONHEIGHT)];
    [self.view addSubview:_toBeScrollView];
    
    self.tobeInfoV = [[TobeTutorForInfoView alloc] initWithFrame:CGRectMake(0, 0, WIDTH, 420)];
    [self.toBeScrollView addSubview:_tobeInfoV];
    
    self.tobeEduV = [[TobeTutorForEduView alloc] initWithFrame:CGRectMake(0, _tobeInfoV.frame.origin.y + _tobeInfoV.frame.size.height, WIDTH, 600)];
    [self.toBeScrollView addSubview:_tobeEduV];
    
    self.tobeWorkV = [[TobeTutorForWorkView alloc] initWithFrame:CGRectMake(0, _tobeEduV.frame.origin.y + _tobeEduV.frame.size.height, WIDTH, 600)];
    [self.toBeScrollView addSubview:_tobeWorkV];
    
    self.tobeServV = [[TobeTutorForServiceView alloc] initWithFrame:CGRectMake(0, _tobeWorkV.frame.origin.y + _tobeWorkV.frame.size.height, WIDTH, HEIGHT * 2 - 40)];
    [self.toBeScrollView addSubview:_tobeServV];
    
    UIButton *submitBT = [UIButton buttonWithType:UIButtonTypeCustom];
    submitBT.frame = CGRectMake(WIDTH / 2.0 - 100, _tobeServV.frame.origin.y + _tobeServV.frame.size.height, 200, 30);
    [submitBT setTitle:@"提交" forState:UIControlStateNormal];
    [submitBT setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
    submitBT.titleLabel.font = [UIFont boldSystemFontOfSize:19.0f];
    [submitBT setBackgroundColor:[UIColor colorWithRed:67 / 255.0 green:172 / 255.0 blue:229 / 255.0 alpha:1.0]];
    submitBT.layer.masksToBounds = YES;
    submitBT.layer.cornerRadius = 15.0f;
    [self.toBeScrollView addSubview:submitBT];
    
    [submitBT addTarget:self action:@selector(submitAction:) forControlEvents:UIControlEventTouchUpInside];
    
    _toBeScrollView.contentSize = CGSizeMake(WIDTH, submitBT.frame.origin.y + submitBT.frame.size.height + 20);
}

#pragma mark -- viewWillAppear & viewWillDisappear
-(void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    
    self.navigationController.navigationBarHidden = YES;
    self.tabBarController.tabBar.hidden = YES;
}

-(void)viewWillDisappear:(BOOL)animated
{
    [super viewWillDisappear:animated];
    
    self.navigationController.navigationBarHidden = YES;
    self.tabBarController.tabBar.hidden = YES;
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
