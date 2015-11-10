//
//  QuerryInfoView.m
//  one_mile_01
//
//  Created by 王雅蓉 on 15/8/26.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import "QuerryInfoView.h"

@interface QuerryInfoView ()
{
    UILabel *clientL;
    UILabel *phoneNumL;
    UILabel *chatL;
    UILabel *emailL;
}

@property (nonatomic, strong) UIView *myView;

@end

@implementation QuerryInfoView

-(instancetype)initWithFrame:(CGRect)frame
{
    if (self = [super initWithFrame:frame]) {
        
        self.userQueryM = [[UserInfoModel alloc] init];
        
        [self createSubviews];
    }
    
    return self;
}

-(void)createSubviews
{
    //NSLog(@"1111");
    self.myView = [[UIView alloc] initWithFrame:CGRectMake(10, 150, WIDTH - 20, 280)];
    _myView.layer.masksToBounds = YES;
    _myView.layer.cornerRadius = 15.0f;
    _myView.backgroundColor = [UIColor whiteColor];
    
    [self addSubview:_myView];
    
    UILabel *promptL = [[UILabel alloc] initWithFrame:CGRectMake(5, 30, _myView.frame.size.width - 10, 40)];
    //promptL.backgroundColor = [UIColor yellowColor];
    promptL.textAlignment = NSTextAlignmentCenter;
    promptL.font = [UIFont systemFontOfSize:14.0f];
    promptL.text = @"预约前，请您确认自己的个人信息，以确保导师联系您";
    promptL.numberOfLines = 2;
    [_myView addSubview:promptL];
    
    //真实姓名
    clientL = [[UILabel alloc] initWithFrame:CGRectMake(promptL.frame.origin.x + 10, promptL.frame.origin.y + promptL.frame.size.height + 15, 80, 30)];
    //clientL.backgroundColor = [UIColor yellowColor];
    clientL.textAlignment = NSTextAlignmentCenter;
    clientL.font = [UIFont systemFontOfSize:15.0f];
    clientL.text = @"真实姓名";
    [_myView addSubview:clientL];
    
    UILabel *demoL1 = [[UILabel alloc] initWithFrame:CGRectMake(clientL.frame.origin.x + clientL.frame.size.width, clientL.frame.origin.y, promptL.frame.size.width - clientL.frame.size.width - 20, 30)];
    demoL1.layer.masksToBounds = YES;
    demoL1.layer.cornerRadius = 15.0f;
    demoL1.layer.borderWidth = 1.0f;
    demoL1.layer.borderColor = [[UIColor lightGrayColor] CGColor];
    [_myView addSubview:demoL1];
    
    self.clientTF = [[UITextField alloc] initWithFrame:CGRectMake(demoL1.frame.origin.x + 5, demoL1.frame.origin.y + 2, demoL1.frame.size.width - 10, demoL1.frame.size.height - 4)];
    //self.clientTF.backgroundColor = [UIColor yellowColor];
    //self.clientTF.userInteractionEnabled = NO;
    self.clientTF.returnKeyType = UIReturnKeyDone;
    self.clientTF.font = [UIFont systemFontOfSize:15.0f];
    [_myView addSubview:_clientTF];
    
    self.clientTF.delegate = self;
    
    //手机号码
    phoneNumL = [[UILabel alloc] initWithFrame:CGRectMake(clientL.frame.origin.x, clientL.frame.origin.y + clientL.frame.size.height + 5, clientL.frame.size.width, clientL.frame.size.height)];
    phoneNumL.textAlignment = NSTextAlignmentCenter;
    phoneNumL.text = @"手机号码";
    phoneNumL.font = [UIFont systemFontOfSize:15.0f];
    //phoneNumL.backgroundColor = [UIColor yellowColor];
    [_myView addSubview:phoneNumL];
    
    UILabel *demoL2 = [[UILabel alloc] initWithFrame:CGRectMake(phoneNumL.frame.origin.x + phoneNumL.frame.size.width, phoneNumL.frame.origin.y, demoL1.frame.size.width, demoL1.frame.size.height)];
    demoL2.layer.masksToBounds = YES;
    demoL2.layer.cornerRadius = 15.0f;
    demoL2.layer.borderWidth = 1.0f;
    demoL2.layer.borderColor = [[UIColor lightGrayColor] CGColor];
    [_myView addSubview:demoL2];
    
    self.phoneNumTF = [[UITextField alloc] initWithFrame:CGRectMake(_clientTF.frame.origin.x, demoL2.frame.origin.y + 2, _clientTF.frame.size.width, _clientTF.frame.size.height)];
    //self.phoneNumTF.userInteractionEnabled = NO;
    //self.phoneNumTF.backgroundColor = [UIColor yellowColor];
    self.phoneNumTF.font = [UIFont systemFontOfSize:15.0f];
    self.phoneNumTF.returnKeyType = UIReturnKeyDone;
    [_myView addSubview:_phoneNumTF];
    
    self.phoneNumTF.delegate = self;
    
    //微信
    chatL = [[UILabel alloc] initWithFrame:CGRectMake(phoneNumL.frame.origin.x, phoneNumL.frame.origin.y + phoneNumL.frame.size.height + 5, phoneNumL.frame.size.width, phoneNumL.frame.size.height)];
    chatL.textAlignment = NSTextAlignmentCenter;
    chatL.text = @"微信";
    chatL.font = [UIFont systemFontOfSize:15.0f];
    //chatL.backgroundColor = [UIColor yellowColor];
    [_myView addSubview:chatL];
    
    UILabel *demoL3 = [[UILabel alloc] initWithFrame:CGRectMake(chatL.frame.origin.x + chatL.frame.size.width, chatL.frame.origin.y, demoL1.frame.size.width, demoL1.frame.size.height)];
    demoL3.layer.masksToBounds = YES;
    demoL3.layer.cornerRadius = 15.0f;
    demoL3.layer.borderWidth = 1.0f;
    demoL3.layer.borderColor = [[UIColor lightGrayColor] CGColor];
    [_myView addSubview:demoL3];
    
    self.chatTF = [[UITextField alloc] initWithFrame:CGRectMake(_phoneNumTF.frame.origin.x, demoL3.frame.origin.y + 2, _phoneNumTF.frame.size.width, _phoneNumTF.frame.size.height)];
    //self.chatTF.userInteractionEnabled = NO;
    //self.chatTF.backgroundColor = [UIColor yellowColor];
    self.chatTF.font = [UIFont systemFontOfSize:15.0f];
    self.chatTF.returnKeyType = UIReturnKeyDone;
    [_myView addSubview:_chatTF];
    
    self.chatTF.delegate = self;
    
    //邮箱
    emailL = [[UILabel alloc] initWithFrame:CGRectMake(chatL.frame.origin.x, chatL.frame.origin.y + chatL.frame.size.height + 5, chatL.frame.size.width, chatL.frame.size.height)];
    emailL.textAlignment = NSTextAlignmentCenter;
    emailL.text = @"邮箱";
    emailL.font = [UIFont systemFontOfSize:15.0f];
    //emailL.backgroundColor = [UIColor yellowColor];
    [_myView addSubview:emailL];
    
    UILabel *demoL4 = [[UILabel alloc] initWithFrame:CGRectMake(emailL.frame.origin.x + emailL.frame.size.width, emailL.frame.origin.y, demoL1.frame.size.width, demoL1.frame.size.height)];
    demoL4.layer.masksToBounds = YES;
    demoL4.layer.cornerRadius = 15.0f;
    demoL4.layer.borderWidth = 1.0f;
    demoL4.layer.borderColor = [[UIColor lightGrayColor] CGColor];
    [_myView addSubview:demoL4];
    
    self.emailTF = [[UITextField alloc] initWithFrame:CGRectMake(_chatTF.frame.origin.x, demoL4.frame.origin.y + 2, _chatTF.frame.size.width, _chatTF.frame.size.height)];
    //self.emailTF.userInteractionEnabled = NO;
    //self.emailTF.backgroundColor = [UIColor yellowColor];
    self.emailTF.font = [UIFont systemFontOfSize:15.0f];
    self.emailTF.returnKeyType = UIReturnKeyDone;
    [_myView addSubview:_emailTF];
    
    self.emailTF.delegate = self;
    
    //确认和取消
    UIButton *cancelBT = [UIButton buttonWithType:UIButtonTypeCustom];
    cancelBT.frame = CGRectMake(_myView.center.x, _emailTF.frame.origin.y + _emailTF.frame.size.height + 15, _myView.frame.size.width / 2.0 / 2.0 - 10, 30);
    cancelBT.layer.borderWidth = 1.0f;
    cancelBT.layer.borderColor = [[UIColor colorWithRed:232 / 255.0 green:234 / 255.0 blue:235 / 255.0 alpha:1.0] CGColor];
    [cancelBT setTitle:@"取  消" forState:UIControlStateNormal];
    cancelBT.layer.masksToBounds = YES;
    cancelBT.layer.cornerRadius = 15.0f;
    [cancelBT setTitleColor:[UIColor blackColor] forState:UIControlStateNormal];
    cancelBT.titleLabel.font = [UIFont systemFontOfSize:15.0f];
    [_myView addSubview:cancelBT];
    
    [cancelBT addTarget:self action:@selector(buttonAction:) forControlEvents:UIControlEventTouchUpInside];
    
    UIButton *okBT = [UIButton buttonWithType:UIButtonTypeCustom];
    okBT.frame = CGRectMake(cancelBT.frame.origin.x + cancelBT.frame.size.width + 5, cancelBT.frame.origin.y, cancelBT.frame.size.width, cancelBT.frame.size.height);
    [okBT setBackgroundColor:[UIColor colorWithRed:71 / 255.0 green:173 / 255.0 blue:227 / 255.0 alpha:1.0]];
    [okBT setTitle:@"确认下单" forState:UIControlStateNormal];
    okBT.layer.masksToBounds = YES;
    okBT.layer.cornerRadius = 15.0f;
    [okBT setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
    okBT.titleLabel.font = [UIFont systemFontOfSize:15.0f];
    [_myView addSubview:okBT];
    
    [okBT addTarget:self action:@selector(buttonAction:) forControlEvents:UIControlEventTouchUpInside];
    
    UILabel *alertL = [[UILabel alloc] initWithFrame:CGRectMake(_myView.frame.origin.x + 10, _myView.frame.origin.y + _myView.frame.size.height + 5, _myView.frame.size.width - 20, 30)];
    alertL.font = [UIFont systemFontOfSize:12.0f];
    alertL.numberOfLines = 2;
    alertL.text = @"您预留的联系方式是导师唯一可以联系您的方式，请确认无误";
    alertL.textColor = [UIColor grayColor];
    //alertL.backgroundColor = [UIColor yellowColor];
    [self addSubview:alertL];
}

-(void)buttonAction:(UIButton *)button
{
    if ([button.titleLabel.text isEqualToString:@"取  消"]) {
        
        [self.myDelegate cancelToBack];
    } else if ([button.titleLabel.text isEqualToString:@"确认下单"]) {
        
        [self.myDelegate querryToPay:_clientTF.text withPhone:_phoneNumTF.text withChat:_chatTF.text withEmail:_emailTF.text];
    }
}

#pragma mark -- 获取当前日期
-(NSString *)getCurrentDate
{
    //获得系统时间
    NSDate *senddate = [NSDate date];
    NSDateFormatter *dateformatter = [[NSDateFormatter alloc] init];
    [dateformatter setDateFormat:[NSString stringWithFormat:@"%@时%@分", @"HH", @"mm"]];
    NSString *locationString = [dateformatter stringFromDate:senddate];
    //[dateformatter setDateFormat:@"YYYY-MM-dd-HH-mm-ss"];
    //NSString *morelocationString = [dateformatter stringFromDate:senddate];
    
    //获得系统日期
    NSCalendar *cal = [NSCalendar currentCalendar];
    NSUInteger unitFlags = NSDayCalendarUnit|NSMonthCalendarUnit|NSYearCalendarUnit;
    NSDateComponents *conponent= [cal components:unitFlags fromDate:senddate];
    NSInteger year = [conponent year];
    NSInteger month = [conponent month];
    NSInteger day = [conponent day];
    NSString *nsDateString = [NSString  stringWithFormat:@"%4ld年%ld月%ld日", (long)year, month, day];
    
    return [NSString stringWithFormat:@"%@%@", nsDateString, locationString];
}

-(void)setUserQueryM:(UserInfoModel *)userQueryM
{
    //NSLog(@"1212");
    self.clientTF.text = userQueryM.name;
    self.phoneNumTF.text = userQueryM.phone;
    self.chatTF.text = @"暂时没有";
    self.emailTF.text = userQueryM.email;
}

#pragma mark -- textfieldDelegate
-(void)textFieldDidBeginEditing:(UITextField *)textField
{
    [UIView animateWithDuration:0.3f animations:^{
        
        self.myView.frame = CGRectMake(10, 50, WIDTH - 20, 280);
    } completion:^(BOOL finished) {
        
        
    }];
}

-(void)textFieldDidEndEditing:(UITextField *)textField
{
    
    [UIView animateWithDuration:0.3f animations:^{
        
        self.myView.frame = CGRectMake(10, 150, WIDTH - 20, 280);
    } completion:^(BOOL finished) {
        
        [textField resignFirstResponder];
    }];
}

-(BOOL)textFieldShouldReturn:(UITextField *)textField
{
    [textField resignFirstResponder];
    
    return NO;
}

/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
*/

@end
