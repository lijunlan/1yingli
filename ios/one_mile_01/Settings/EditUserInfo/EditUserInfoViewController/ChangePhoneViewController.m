//
//  ChangePhoneViewController.m
//  one_mile_01
//
//  Created by 王雅蓉 on 15/9/6.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import "ChangePhoneViewController.h"

@interface ChangePhoneViewController ()

@property (nonatomic, strong) UIButton *getCheckNoBT;

@end

@implementation ChangePhoneViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    self.view.backgroundColor = [UIColor whiteColor];
    
    [self createSubviews];
}

-(void)createSubviews
{
    UILabel *titleLabel = [[UILabel alloc]initWithFrame:CGRectMake(self.view.frame.size.width / 2 - 50, 40,100, 30)];
    titleLabel.text = @"修改手机";
    titleLabel.font = [UIFont fontWithName:@"TimesNewRomanPS-BoldMT" size:20.0f];
    titleLabel.textColor = [UIColor lightGrayColor];
    [self.view addSubview:titleLabel];
    
    UIButton *button = [UIButton buttonWithType:UIButtonTypeCustom];
    button.frame = CGRectMake(20, titleLabel.frame.origin.y, PUSHANDPOPBUTTONSIZE, PUSHANDPOPBUTTONSIZE);
    [button setBackgroundImage:[UIImage imageNamed:@"pop_dark.png"] forState:UIControlStateNormal];
    button.backgroundColor = [UIColor whiteColor];
    [button addTarget:self action:@selector(backButton:) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:button];
    
    UIImageView *telIV = [[UIImageView alloc] initWithFrame:CGRectMake(40 + 5, NAVIGATIONHEIGHT + 50, 20, 30)];
    telIV.image = [UIImage imageNamed:@"telephone.png"];
    [self.view addSubview:telIV];
    
    self.telNumTF = [[UITextField alloc] initWithFrame:CGRectMake(telIV.frame.origin.x + telIV.frame.size.width + 8, telIV.frame.origin.y, WIDTH - 80 - 8 - telIV.frame.size.width - 5, 30)];
    self.telNumTF.backgroundColor = [UIColor yellowColor];
    self.telNumTF.placeholder = @"手机";
    self.telNumTF.textColor = [UIColor lightGrayColor];
    self.telNumTF.font = [UIFont systemFontOfSize:15.0f];
    [self.view addSubview:_telNumTF];
    
    UILabel *aLine = [[UILabel alloc] initWithFrame:CGRectMake(40, telIV.frame.origin.y + telIV.frame.size.height + 10, WIDTH - 80, 1)];
    aLine.backgroundColor = [UIColor colorWithRed:229 / 255.0 green:229 / 255.0 blue:229 / 255.0 alpha:1.0];
    [self.view addSubview:aLine];
    
    UIImageView *checkNoIV = [[UIImageView alloc] initWithFrame:CGRectMake(telIV.frame.origin.x, aLine.frame.origin.y + aLine.frame.size.height + 10, telIV.frame.size.width, telIV.frame.size.height)];
    checkNoIV.image = [UIImage imageNamed:@"lock.png"];
    [self.view addSubview:checkNoIV];
    
    self.checkNoTF = [[UITextField alloc] initWithFrame:CGRectMake(_telNumTF.frame.origin.x, checkNoIV.frame.origin.y, _telNumTF.frame.size.width - 105, _telNumTF.frame.size.height)];
    self.checkNoTF.backgroundColor = [UIColor yellowColor];
    self.checkNoTF.placeholder = @"验证码";
    self.checkNoTF.textColor = [UIColor lightGrayColor];
    self.checkNoTF.font = [UIFont systemFontOfSize:15.0f];
    [self.view addSubview:_checkNoTF];
    
    self.getCheckNoBT = [UIButton buttonWithType:UIButtonTypeCustom];
    _getCheckNoBT.frame = CGRectMake(_checkNoTF.frame.origin.x + _checkNoTF.frame.size.width + 5, _checkNoTF.frame.origin.y, _telNumTF.frame.size.width - _checkNoTF.frame.size.width - 10, _telNumTF.frame.size.height);
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
    [self getChangeCheckNo];
}

//提交
-(void)submitButton:(UIButton *)button
{
    [self submitChangeTelNum];
}

#pragma mark -- 请求数据
//获取验证码
-(void)getChangeCheckNo
{
    NSString *urlStr = @"http://service.1yingli.cn/yiyingliService/manage";
    
    [AFNConnect AFNConnectWithUrl:urlStr withBodyData:[AFNConnect createDataForCheckNo:self.telNumTF.text] connectBlock:^(id data) {
        
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
-(void)submitChangeTelNum
{
    [AFNConnect AFNConnectWithUrl:HTTPREQUEST withBodyData:[AFNConnect createDataForChangePhoneNo:self.userIDforChange withCheckNo:self.checkNoTF.text withPhone:self.telNumTF.text] connectBlock:^(id data) {
        
        NSDictionary *dic = (NSDictionary *)[NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingMutableContainers error:nil];
        
        NSString *state = [dic objectForKey:@"state"];
        
        if ([state isEqualToString:@"success"]) {
            
            [self dismissViewControllerAnimated:YES completion:^{
                
                [self.myDelegate changeSuccess:self.telNumTF.text];
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
