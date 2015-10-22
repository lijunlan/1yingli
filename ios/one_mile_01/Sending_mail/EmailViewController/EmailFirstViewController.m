//
//  EmailFirstViewController.m
//  one_mile_01
//
//  Created by 王进帅 on 15/9/2.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import "EmailFirstViewController.h"
#import "LoginViewController.h"
#import "LoginModal.h"

@interface EmailFirstViewController ()<lcForEmailInterface>
@property (nonatomic, strong) LoginModal *loginUser;

@end

@implementation EmailFirstViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    self.view.backgroundColor = [UIColor yellowColor];
    self.navigationController.navigationBar.hidden = YES;
    UIImageView *imageV = [[UIImageView alloc]initWithFrame:[[UIScreen mainScreen]bounds]];
    imageV.image = [UIImage imageNamed:@"message_bg.png"];
    
    imageV.userInteractionEnabled = YES;
    [self.view addSubview:imageV];
    
    UIView *view = [[UIView alloc]init];
    [imageV addSubview:view];
    view.layer.masksToBounds = YES;
    view.layer.cornerRadius = 10;
    [view mas_makeConstraints:^(MASConstraintMaker *make) {
        make.bottom.equalTo(imageV.mas_bottom).offset(-100);
        make.left.equalTo(imageV.mas_left).offset(10);
        make.right.equalTo(imageV.mas_right).offset(-10);
        if (iPhone5) {
            make.height.offset(200);
        }
        make.height.offset(220);
        
    }];
    view.backgroundColor = [UIColor whiteColor];
    
    
    UILabel *label1 = [[UILabel alloc]init];
    label1.text = @"还没收到消息";
    [view addSubview:label1];
    [label1 mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(view.mas_top).offset(25);
        make.left.equalTo(view.mas_left).offset(self.view.frame.size.width / 2 - 75);
        make.right.equalTo(view.mas_right).offset(-(self.view.frame.size.width / 2 - 75));
        make.bottom.equalTo(view.mas_bottom).offset(-160);
        make.centerX.equalTo(view.mas_centerX);
    }];
    label1.font = [UIFont systemFontOfSize:19 weight:10];
    
    
    
    UILabel *label2 = [[UILabel alloc]init];
    [view addSubview:label2];
    [label2 mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(view.mas_top).offset(60);
        make.left.equalTo(view.mas_left).offset(self.view.frame.size.width / 2 - 125);
        make.right.equalTo(view.mas_right).offset(-(self.view.frame.size.width / 2 - 125));
        make.bottom.equalTo(view.mas_bottom).offset(-100);
    }];
    label2.numberOfLines = 2;
    label2.text = @"找到想要咨询的导师,预约时告诉导师想要咨询的。";
    label2.textColor = [UIColor colorWithRed:128 / 255.0 green:128 / 255.0 blue:128 / 255.0 alpha:1.0];
    label2.font = [UIFont systemFontOfSize:15 weight:10];
    
    
    
    
    UIButton *button1 = [[UIButton alloc]init];
    [view addSubview:button1];
    [button1 mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(view.mas_left).offset(50);
        make.right.equalTo(view.mas_right).offset(-50);
        make.bottom.equalTo(view.mas_bottom).offset(-20);
        make.height.offset(45);
    }];
    button1.backgroundColor = [UIColor colorWithRed:71/255.0 green:172/255.0 blue:226/255.0 alpha:1.0];
    button1.layer.masksToBounds = YES;
    button1.layer.cornerRadius = 10;
    [button1 setTitle:@"登录" forState:UIControlStateNormal];
    [button1 setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
    button1.userInteractionEnabled = YES;
    [button1 addTarget:self action:@selector(button4Action:) forControlEvents:UIControlEventTouchUpInside];
    
}
-(void)button4Action:(UIButton *)button{
    LoginViewController *loginVC = [[LoginViewController alloc]init];
    
    loginVC.myEmailDelegate = self;
    
    [self presentViewController:loginVC animated:YES completion:^{
        
    }];
}

#pragma mark -- loginViewControllerDelegate
-(void)transitionVIewToEmail:(LoginModal *)users
{
    if ([[[NSUserDefaults standardUserDefaults] objectForKey:@"isLogin"] isEqualToString:@"1"]) {
        
        self.loginUser = users;
        
        [self.parentViewController.view sendSubviewToBack:self.view];
    }
}



#pragma mark -- viewWillAppear & viewWillDisappear
-(void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
}

-(void)viewWillDisappear:(BOOL)animated
{
    [super viewWillDisappear:animated];
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
