//
//  forgetPasswordViewController.m
//  one_mile_01
//
//  Created by 王进帅 on 15/8/28.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import "forgetPasswordViewController.h"

@interface forgetPasswordViewController ()

@end

@implementation forgetPasswordViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    //    返回按钮
    UIButton *button = [UIButton buttonWithType:UIButtonTypeCustom];
    button.frame = CGRectMake(20, 45, PUSHANDPOPBUTTONSIZE , PUSHANDPOPBUTTONSIZE);
    [button setBackgroundImage:[UIImage imageNamed:@"pop_dark.png"] forState:UIControlStateNormal];
    button.backgroundColor = [UIColor clearColor];
    [button addTarget:self action:@selector(backbutton:) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:button];
    
//    忘记密码
    self.navigationController.navigationBar.hidden = YES;
    UILabel *titleLabel = [[UILabel alloc]initWithFrame:CGRectMake(self.view.frame.size.width / 2 - 50, button.frame.origin.y,100, 30)];
    titleLabel.text = @"忘记密码";
    titleLabel.font = [UIFont fontWithName:@"TimesNewRomanPS-BoldMT" size:20.0f];
    titleLabel.textColor = [UIColor lightGrayColor];
    [self.view addSubview:titleLabel];
    
//    小手机
    UIImageView *imageView = [[UIImageView alloc]initWithFrame:CGRectMake(titleLabel.frame.origin.x - 20, titleLabel.frame.origin.y + 130, 20, 20)];
    imageView.image = [UIImage imageNamed:@"loginID.png"];
    [self.view addSubview:imageView];
    
//    注册手机邮箱
    UILabel *label1 = [[UILabel alloc]initWithFrame:CGRectMake(imageView.frame.origin.x + imageView.frame.size.width + 5, imageView.frame.origin.y - 5, 200, 30)];
    label1.text = @"注册手机/邮箱";
    label1.textColor = [UIColor lightGrayColor];
    label1.font = [UIFont systemFontOfSize:15];
    [self.view addSubview:label1];
    
    UITextField *text = [[UITextField alloc]initWithFrame:CGRectMake(button.frame.origin.x + 10 , label1.frame.origin.y + label1.frame.size.height + 15, self.view.frame.size.width - button.frame.origin.x * 2 - 20, 40)];
    text.layer.masksToBounds = YES;
    text.layer.borderWidth  = 1.0;
    text.layer.borderColor = [UIColor lightGrayColor].CGColor;
    [self.view addSubview:text];
    
    UIButton *button1 = [[UIButton alloc]initWithFrame:CGRectMake(text.frame.origin.x,text.frame.size.height +text.frame.origin.y + 20,text.frame.size.width,text.frame.size.height)];
    button1.backgroundColor = [UIColor colorWithRed:71/255.0 green:172/255.0 blue:226/255.0 alpha:1.0];
    button1.layer.masksToBounds = YES;
    button1.layer.cornerRadius = 10;
    [button1 setTitle:@"找回密码" forState:UIControlStateNormal];
    [button1 setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
    [button1 addTarget:self action:@selector(button4Action:) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:button1];
    
    // Do any additional setup after loading the view.
}
//找回密码
-(void)button4Action:(UIButton *)button{


}

-(void)backbutton:(UIButton *)button{

[self dismissViewControllerAnimated:YES completion:^{
    
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
