//
//  AccountSecurityViewController.m
//  one_mile_01
//
//  Created by 王进帅 on 15/8/27.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import "AccountSecurityViewController.h"

@interface AccountSecurityViewController ()

@end

@implementation AccountSecurityViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    self.view.backgroundColor = [UIColor whiteColor];
    
    UIView *backView = [[UIView alloc]initWithFrame:CGRectMake(self.view.frame.origin.x ,self.view.frame.origin.y + NAVIGATIONHEIGHT,self.view.frame.size.width , self.view.frame.size.height - NAVIGATIONHEIGHT)];
    backView.backgroundColor = [UIColor colorWithRed:223/255.0 green:223/255.0 blue:223/255.0 alpha:1.0];
   [self.view  addSubview:backView];

    //    返回按钮
    UIButton *button = [UIButton buttonWithType:UIButtonTypeCustom];
    button.frame = CGRectMake(20, 40, PUSHANDPOPBUTTONSIZE, PUSHANDPOPBUTTONSIZE);
    [button setBackgroundImage:[UIImage imageNamed:@"pop_dark.png"] forState:UIControlStateNormal];
    button.backgroundColor = [UIColor clearColor];
    [button addTarget:self action:@selector(backbutton:) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:button];
    
    self.navigationController.navigationBar.hidden = YES;
    UILabel *titleLabel = [[UILabel alloc]initWithFrame:CGRectMake(WIDTH / 2.0 - 70, 30, 140, NAVIGATIONHEIGHT - 30)];
    titleLabel.text = @"账户安全";
    titleLabel.font = [UIFont fontWithName:@"TimesNewRomanPS-BoldMT" size:20.0f];
    titleLabel.textAlignment = NSTextAlignmentCenter;
    titleLabel.textColor = [UIColor lightGrayColor];
    [self.view addSubview:titleLabel];
    
//原始密码背景图
    UIImageView *imageView1 = [[UIImageView alloc]initWithFrame:CGRectMake(backView.frame.origin.x + 10,20, backView.frame.size.width - 20, 40)];
    imageView1.image = [UIImage imageNamed:@"kuangjia.png"];
    imageView1.userInteractionEnabled = YES;
    [backView addSubview:imageView1];
    
    self.label1 = [[UILabel alloc]initWithFrame:CGRectMake(4, 5, 100, 30)];
    self.label1.font = [UIFont systemFontOfSize:18];
    self.label1.text = @"  原始密码:";
    self.label1.textColor = [UIColor blackColor];
    [imageView1 addSubview:self.label1];
    
    self.textField1 = [[UITextField alloc]initWithFrame:CGRectMake(self.label1.frame.origin.x + self.label1.frame.size.width,3, imageView1.frame.size.width - self.label1.frame.origin.x - self.label1.frame.size.width - 10, 30)];
    self.textField1.secureTextEntry = YES;
//    self.textField1.backgroundColor = [UIColor yellowColor];
    [imageView1 addSubview:self.textField1];
    
//    新密码
    UIImageView *imageView2 = [[UIImageView alloc]initWithFrame:CGRectMake(imageView1.frame.origin.x,imageView1.frame.origin.y + imageView1.frame.size.height + 20, imageView1.frame.size.width , 120)];
    imageView2.userInteractionEnabled = YES;
    imageView2.image = [UIImage imageNamed:@"kuangjia.png"];
    [backView addSubview:imageView2];
    
    UILabel *label2 = [[UILabel alloc]initWithFrame:CGRectMake(4, 15, 100, 30)];
    label2.font = [UIFont systemFontOfSize:18];
    label2.text = @"    新密码:";
    label2.textColor = [UIColor blackColor];
    [imageView2 addSubview:label2];
    
    self.textField2 = [[UITextField alloc]initWithFrame:CGRectMake(label2.frame.origin.x + label2.frame.size.width ,label2.frame.origin.y, imageView2.frame.size.width - label2.frame.origin.x - label2.frame.size.width - 10, 30)];
    self.textField2.secureTextEntry = YES;
//    self.textField2.backgroundColor = [UIColor yellowColor];
    [imageView2 addSubview:self.textField2];
    
//    线
    UILabel *label3 = [[UILabel alloc]initWithFrame:CGRectMake(imageView2.frame.origin.x , imageView2.frame.size.height / 2, imageView2.frame.size.width - 25, 0.5)];
    label3.backgroundColor = [UIColor colorWithRed:128 / 255.0 green:128 / 255.0 blue:128 / 255.0 alpha:1.0];
    [imageView2 addSubview:label3];

//    确认密码
    UILabel *label4 = [[UILabel alloc]initWithFrame:CGRectMake(label2.frame.origin.x, label3.frame.origin.y + 10, label2.frame.size.width, label2.frame.size.height)];
    label4.font = [UIFont systemFontOfSize:18];
    label4.text = @"  确认密码:";
    label4.textColor = [UIColor blackColor];
    [imageView2 addSubview:label4];

    self.textField3 = [[UITextField alloc]initWithFrame:CGRectMake(label4.frame.origin.x + label4.frame.size.width ,label4.frame.origin.y, self.textField2.frame.size.width, self.textField2.frame.size.height)];
    self.textField3.secureTextEntry = YES;
//    self.textField3.backgroundColor = [UIColor yellowColor];
    [imageView2 addSubview:self.textField3];

    UIButton *button1 = [[UIButton alloc]initWithFrame:CGRectMake(self.view.frame.size.width / 2 - 120,imageView2.frame.size.height +imageView2.frame.origin.y + 50,240,40)];
    button1.backgroundColor = [UIColor colorWithRed:71/255.0 green:172/255.0 blue:226/255.0 alpha:1.0];
    button1.layer.masksToBounds = YES;
    button1.layer.cornerRadius = 10;
    [button1 setTitle:@"提交" forState:UIControlStateNormal];
    [button1 setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
    [button1 addTarget:self action:@selector(button4Action:) forControlEvents:UIControlEventTouchUpInside];
    [backView addSubview:button1];

    //结束编辑
    UIGestureRecognizer *resignKeyboard = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(backgroundTapped:)];
    [self.view addGestureRecognizer:resignKeyboard];
    
    
    // Do any additional setup after loading the view.
}
//键盘弹回，退出编辑模式
-(void)backgroundTapped:(UITapGestureRecognizer *)tap
{
    [self.view endEditing:YES];
}

//修改密码
-(void)button4Action:(UIButton *)button{

    [self submitChangePassWord];
}


-(void)backbutton:(UIButton *)button{
    
    [self.navigationController popViewControllerAnimated:YES];
}


//提交，更改手机
-(void)submitChangePassWord
{
    if ([self isValidatePassword:self.textField2.text]) {
        if ([self.textField2.text isEqualToString:self.textField3.text]) {
            
            [AFNConnect AFNConnectWithUrl:HTTPREQUEST withBodyData:[AFNConnect createDataForChangePass:self.textField2.text withOldPassword:self.textField1.text withUid:[[[NSUserDefaults standardUserDefaults] objectForKey:@"userInfo"] objectForKey:@"uid"]] connectBlock:^(id data) {
                NSDictionary *dic = (NSDictionary *)[NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingMutableContainers error:nil];
                
                NSString *state = [dic objectForKey:@"state"];
        
                if ([state isEqualToString:@"success"]) {
                    
                    [self.navigationController popViewControllerAnimated:YES];
                    
                } else {
                    
                    [self setAlertView:@"提示" withMessage:@"修改失败" buttonTitle:@"确定"];
                }
            }];
        }else{
            UIAlertView *alter = [[UIAlertView alloc]initWithTitle:@"提示" message:@"您输入的密码与上次不相等" delegate:self cancelButtonTitle:@"取消" otherButtonTitles:@"确认", nil];
            [alter show];
        }
    }else{
        UIAlertView *alter = [[UIAlertView alloc]initWithTitle:@"提示" message:@"您输入新密码格式不符" delegate:self cancelButtonTitle:@"取消" otherButtonTitles:@"确认", nil];
        [alter show];
    
    }
}

#pragma mark -- 提示框
-(void)setAlertView:(NSString *)title withMessage:(NSString *)message buttonTitle:(NSString *)buttonTitle
{
    UIAlertView *alert = [[UIAlertView alloc] initWithTitle:title message:message delegate:self cancelButtonTitle:buttonTitle otherButtonTitles:nil];
    [self.view addSubview:alert];
    
    [alert show];
}

//密码验证
-(BOOL)isValidatePassword:(NSString *)password
{
    NSString *passRegex = @"[A-Za-z0-9]{6,20}";
    NSPredicate *passText = [NSPredicate predicateWithFormat:@"SELF MATCHES %@", passRegex];
    
    return [passText evaluateWithObject:password];
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
