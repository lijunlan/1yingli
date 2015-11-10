//
//  ChangeEmailViewController.m
//  one_mile_01
//
//  Created by 王雅蓉 on 15/9/7.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import "ChangeEmailViewController.h"

@interface ChangeEmailViewController ()

@property (nonatomic, strong) UIButton *getCheckNoBT;

@end

@implementation ChangeEmailViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    self.view.backgroundColor = [UIColor whiteColor];
    
    [self createSubviews];
}

-(void)createSubviews
{
    UILabel *titleLabel = [[UILabel alloc]initWithFrame:CGRectMake(self.view.frame.size.width / 2 - 50, 40,100, 30)];
    titleLabel.text = @"修改邮箱";
    titleLabel.font = [UIFont fontWithName:@"TimesNewRomanPS-BoldMT" size:20.0f];
    titleLabel.textColor = [UIColor lightGrayColor];
    [self.view addSubview:titleLabel];
    
    UIButton *button = [UIButton buttonWithType:UIButtonTypeCustom];
    button.frame = CGRectMake(20, titleLabel.frame.origin.y, PUSHANDPOPBUTTONSIZE, PUSHANDPOPBUTTONSIZE);
    [button setBackgroundImage:[UIImage imageNamed:@"pop_dark.png"] forState:UIControlStateNormal];
    button.backgroundColor = [UIColor whiteColor];
    [button addTarget:self action:@selector(backButton:) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:button];
    
    UIImageView *emailIV = [[UIImageView alloc] initWithFrame:CGRectMake(40 + 5, NAVIGATIONHEIGHT + 50, 20, 30)];
    emailIV.image = [UIImage imageNamed:@"telephone.png"];
    [self.view addSubview:emailIV];
    
    self.emailTF = [[UITextField alloc] initWithFrame:CGRectMake(emailIV.frame.origin.x + emailIV.frame.size.width + 8, emailIV.frame.origin.y, WIDTH - 80 - 8 - emailIV.frame.size.width - 5, 30)];
    self.emailTF.backgroundColor = [UIColor yellowColor];
    self.emailTF.placeholder = @"邮箱";
    self.emailTF.textColor = [UIColor lightGrayColor];
    self.emailTF.font = [UIFont systemFontOfSize:15.0f];
    [self.view addSubview:_emailTF];
    
    UILabel *aLine = [[UILabel alloc] initWithFrame:CGRectMake(40, emailIV.frame.origin.y + emailIV.frame.size.height + 10, WIDTH - 80, 1)];
    aLine.backgroundColor = [UIColor colorWithRed:229 / 255.0 green:229 / 255.0 blue:229 / 255.0 alpha:1.0];
    [self.view addSubview:aLine];
    
    UIImageView *checkNoIV = [[UIImageView alloc] initWithFrame:CGRectMake(emailIV.frame.origin.x, aLine.frame.origin.y + aLine.frame.size.height + 10, emailIV.frame.size.width, emailIV.frame.size.height)];
    checkNoIV.image = [UIImage imageNamed:@"lock.png"];
    [self.view addSubview:checkNoIV];
    
    self.emCheckNoTF = [[UITextField alloc] initWithFrame:CGRectMake(_emailTF.frame.origin.x, checkNoIV.frame.origin.y, _emailTF.frame.size.width - 105, _emailTF.frame.size.height)];
    self.emCheckNoTF.backgroundColor = [UIColor yellowColor];
    self.emCheckNoTF.placeholder = @"验证码";
    self.emCheckNoTF.textColor = [UIColor lightGrayColor];
    self.emCheckNoTF.font = [UIFont systemFontOfSize:15.0f];
    [self.view addSubview:_emCheckNoTF];
    
    self.getCheckNoBT = [UIButton buttonWithType:UIButtonTypeCustom];
    _getCheckNoBT.frame = CGRectMake(_emCheckNoTF.frame.origin.x + _emCheckNoTF.frame.size.width + 5, _emCheckNoTF.frame.origin.y, _emailTF.frame.size.width - _emCheckNoTF.frame.size.width - 10, _emailTF.frame.size.height);
    [_getCheckNoBT setTitle:@"获取验证码" forState:UIControlStateNormal];
    _getCheckNoBT.titleLabel.font = [UIFont systemFontOfSize:15.0f];
    [_getCheckNoBT setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
    _getCheckNoBT.layer.masksToBounds = YES;
    _getCheckNoBT.layer.cornerRadius = 5;
    [_getCheckNoBT setBackgroundColor:[UIColor colorWithRed:73 / 255.0 green:173 / 255.0 blue:227 / 255.0 alpha:1.0]];
    [self.view addSubview:_getCheckNoBT];
    
    [_getCheckNoBT addTarget:self action:@selector(getCheckNo:) forControlEvents:UIControlEventTouchUpInside];
    
    UILabel *bLine = [[UILabel alloc] initWithFrame:CGRectMake(aLine.frame.origin.x, checkNoIV.frame.origin.y + checkNoIV.frame.size.height + 10, aLine.frame.size.width, aLine.frame.size.height)];
    bLine.backgroundColor = [UIColor colorWithRed:229 / 255.0 green:229 / 255.0 blue:229 / 255.0 alpha:1.0];
    [self.view addSubview:bLine];
    
    UIButton *subBT = [UIButton buttonWithType: UIButtonTypeCustom];
    subBT.frame = CGRectMake(bLine.frame.origin.x + 5, bLine.frame.origin.y + 20, bLine.frame.size.width - 10, 45);
    [subBT setTitle:@"提交" forState:UIControlStateNormal];
    [subBT setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
    [subBT setBackgroundColor:[UIColor colorWithRed:73 / 255.0 green:173 / 255.0 blue:227 / 255.0 alpha:1.0]];
    subBT.layer.masksToBounds = YES;
    subBT.layer.cornerRadius = 5;
    [self.view addSubview:subBT];
    
    [subBT addTarget:self action:@selector(submitButton:) forControlEvents:UIControlEventTouchUpInside];
    
    UIGestureRecognizer *resignKeyboard = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(endEditText:)];
    [self.view addGestureRecognizer:resignKeyboard];
}

-(void)endEditText:(UITapGestureRecognizer *)tap
{
    [self.view endEditing:YES];
}

#pragma mark -- buttonAction
//返回
-(void)backButton:(UIButton *)button
{
    [self dismissViewControllerAnimated:YES completion:^{
        
        
    }];
}

//获取验证码
-(void)getCheckNo:(UIButton *)button
{
    if (![self isValidateEmail:_emailTF.text]) {
        
        [self setAlertView:@"提示" withMessage:@"邮箱格式不正确，确认后再修改" buttonTitle:@"确定"];
    } else {
        
        [self getChangeCheckNo];
    }
}

//邮箱验证
-(BOOL)isValidateEmail:(NSString *)email
{
    NSString *emailRegex = @"[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}";
    NSPredicate *emailTest = [NSPredicate predicateWithFormat:@"SELF MATCHES %@", emailRegex];
    
    return [emailTest evaluateWithObject:email];
}

//提交
-(void)submitButton:(UIButton *)button
{
    [self submitChangeEmail];
}

#pragma mark -- 请求数据
//获取验证码
-(void)getChangeCheckNo
{
    NSString *urlStr = @"http://service.1yingli.cn/yiyingliService/manage";
    
    [AFNConnect AFNConnectWithUrl:urlStr withBodyData:[AFNConnect createDataForCheckNo:self.emailTF.text] connectBlock:^(id data) {
        
        NSDictionary *dic = (NSDictionary *)[NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingMutableContainers error:nil];
        
        //NSLog(@"%@", dic);
        NSString *state = [dic objectForKey:@"state"];
        
        if ([state isEqualToString:@"success"]) {
            
            [self setAlertView:@"提示" withMessage:@"验证码已发送成功，注意查收验证码" buttonTitle:@"确认"];
            
            [self.getCheckNoBT setTitle:@"获取验证码" forState:UIControlStateNormal];
            [self.getCheckNoBT setTitleColor:[UIColor lightGrayColor] forState:UIControlStateNormal];
            [self.getCheckNoBT setBackgroundColor:[UIColor clearColor]];
        } else {
            
            [self setAlertView:@"提示" withMessage:@"验证码获取失败" buttonTitle:@"确认"];
            
        }
        
    }];
}

//提交，更改手机
-(void)submitChangeEmail
{
    [AFNConnect AFNConnectWithUrl:HTTPREQUEST withBodyData:[AFNConnect createDataForChangeEmail:self.userIDforChangeEm withCheckNo:self.emCheckNoTF.text withEmail:self.emailTF.text] connectBlock:^(id data) {
        
        NSDictionary *dic = (NSDictionary *)[NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingMutableContainers error:nil];
        
        NSString *state = [dic objectForKey:@"state"];
        
        if ([state isEqualToString:@"success"]) {
            
            [self dismissViewControllerAnimated:YES completion:^{
                
                [self.myDelegate changeEmailSuccess:self.emailTF.text];
            }];
        } else {
            
            [self setAlertView:@"提示" withMessage:@"输入的验证码有误" buttonTitle:@"确定"];
        }
    }];
}

#pragma mark -- 提示框
-(void)setAlertView:(NSString *)title withMessage:(NSString *)message buttonTitle:(NSString *)buttonTitle
{
    UIAlertView *alert = [[UIAlertView alloc] initWithTitle:title message:message delegate:self cancelButtonTitle:buttonTitle otherButtonTitles:nil];
    [self.view addSubview:alert];
    
    [alert show];
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
