//
//  TobeTutorForInfoView.m
//  one_mile_01
//
//  Created by 王雅蓉 on 15/10/15.
//  Copyright © 2015年 王雅蓉. All rights reserved.
//

#import "TobeTutorForInfoView.h"

@interface TobeTutorForInfoView ()

@end

@implementation TobeTutorForInfoView

-(instancetype)initWithFrame:(CGRect)frame
{
    if (self = [super initWithFrame:frame]) {
       
        self.backgroundColor = [UIColor whiteColor];
        [self createSubviews];
    }
    
    return self;
}

-(void)createSubviews
{
    UILabel *titleL = [[UILabel alloc] initWithFrame:CGRectMake(5, 0, WIDTH - 10, 50)];
    titleL.numberOfLines = 2;
    titleL.font = [UIFont boldSystemFontOfSize:18.0f];
    titleL.textColor = [UIColor colorWithRed:39 / 255.0 green:56 / 255.0 blue:76 / 255.0 alpha:1.0];
    titleL.text = @"如果您在某个领域有多年经验和独到见解，欢迎申请成为「一英里」的导师。";
    [self addSubview:titleL];
    
    UILabel *subTitleL = [[UILabel alloc] initWithFrame:CGRectMake(5, titleL.frame.origin.y + titleL.frame.size.height, titleL.frame.size.width, 40)];
    subTitleL.numberOfLines = 2;
    subTitleL.font = [UIFont systemFontOfSize:15.0f];
    subTitleL.textColor = [UIColor colorWithRed:39 / 255.0 green:56 / 255.0 blue:76 / 255.0 alpha:1.0];
    subTitleL.text = @"如果您感兴趣，请花10-15分钟填写以下表格，我们将在24小时内与您联系。";
    [self addSubview:subTitleL];
    
//    姓名
    UILabel *nameL = [[UILabel alloc] initWithFrame:CGRectMake(10, subTitleL.frame.origin.y + subTitleL.frame.size.height + 5, WIDTH / 2.0, 30)];
    nameL.text = @"姓名";
    nameL.textColor = [UIColor colorWithRed:39 / 255.0 green:56 / 255.0 blue:76 / 255.0 alpha:1.0];
    [self addSubview:nameL];
    
    UIView *nameV = [[UIView alloc] initWithFrame:CGRectMake(10, nameL.frame.origin.y + nameL.frame.size.height, WIDTH / 2.0, 35)];
    nameV.layer.borderWidth = 1.0f;
    nameV.layer.borderColor = [[UIColor lightGrayColor] CGColor];
    [self addSubview:nameV];
    
    self.nameForTobeTF = [[UITextField alloc] initWithFrame:CGRectMake(15, 0, nameV.frame.size.width - 15, nameV.frame.size.height)];
    self.nameForTobeTF.returnKeyType = UIReturnKeyDone;
    [nameV addSubview:_nameForTobeTF];
    
    UILabel *nameAlert = [[UILabel alloc] initWithFrame:CGRectMake(nameV.frame.origin.x + nameV.frame.size.width + 5, nameV.frame.origin.y + 5, WIDTH - 10 - nameV.frame.size.width - 5, nameV.frame.size.height - 10)];
    nameAlert.text = @"＊(必填)";
    nameAlert.textColor = [UIColor redColor];
    nameAlert.font = [UIFont systemFontOfSize:14.0f];
    [self addSubview:nameAlert];
    
//    手机
    UILabel *phoneL = [[UILabel alloc] initWithFrame:CGRectMake(nameL.frame.origin.x, nameV.frame.origin.y + nameV.frame.size.height + 5, nameV.frame.size.width, nameV.frame.size.height)];
    phoneL.text = @"手机";
    phoneL.textColor = [UIColor colorWithRed:39 / 255.0 green:56 / 255.0 blue:76 / 255.0 alpha:1.0];
    [self addSubview:phoneL];
    
    UIView *phoneV = [[UIView alloc] initWithFrame:CGRectMake(nameV.frame.origin.x, phoneL.frame.origin.y + phoneL.frame.size.height, nameV.frame.size.width, nameV.frame.size.height)];
    phoneV.layer.borderColor = [UIColor lightGrayColor].CGColor;
    phoneV.layer.borderWidth = 1.0f;
    [self addSubview:phoneV];
    
    self.phoneForTobeTF = [[UITextField alloc] initWithFrame:CGRectMake(_nameForTobeTF.frame.origin.x, _nameForTobeTF.frame.origin.y, _nameForTobeTF.frame.size.width, _nameForTobeTF.frame.size.height)];
    self.phoneForTobeTF.returnKeyType = UIReturnKeyDone;
    [phoneV addSubview:_phoneForTobeTF];
    
    UILabel *phoneAlert = [[UILabel alloc] initWithFrame:CGRectMake(phoneV.frame.origin.x + phoneV.frame.size.width + 5, phoneV.frame.origin.y + 5, WIDTH - 10 - phoneV.frame.size.width - 5, phoneV.frame.size.height - 10)];
    phoneAlert.text = @"＊(必填)";
    phoneAlert.textColor = [UIColor redColor];
    phoneAlert.font = [UIFont systemFontOfSize:14.0f];
    [self addSubview:phoneAlert];
    
//    常驻地址
    UILabel *addressL = [[UILabel alloc] initWithFrame:CGRectMake(phoneL.frame.origin.x, phoneV.frame.origin.y + phoneV.frame.size.height + 5, phoneV.frame.size.width, phoneV.frame.size.height)];
    addressL.text = @"常驻地址";
    addressL.textColor = [UIColor colorWithRed:39 / 255.0 green:56 / 255.0 blue:76 / 255.0 alpha:1.0];
    [self addSubview:addressL];
    
    UIView *addressV = [[UIView alloc] initWithFrame:CGRectMake(phoneV.frame.origin.x, addressL.frame.origin.y + addressL.frame.size.height, phoneV.frame.size.width, phoneV.frame.size.height)];
    addressV.layer.borderColor = [UIColor lightGrayColor].CGColor;
    addressV.layer.borderWidth = 1.0f;
    [self addSubview:addressV];
    
    self.addressForTobeTF = [[UITextField alloc] initWithFrame:CGRectMake(_phoneForTobeTF.frame.origin.x, _phoneForTobeTF.frame.origin.y, _phoneForTobeTF.frame.size.width, _phoneForTobeTF.frame.size.height)];
    self.addressForTobeTF.returnKeyType = UIReturnKeyDone;
    [addressV addSubview:_addressForTobeTF];
    
    UILabel *addressAlert = [[UILabel alloc] initWithFrame:CGRectMake(addressV.frame.origin.x + addressV.frame.size.width + 5, addressV.frame.origin.y + 5, WIDTH - 10 - addressV.frame.size.width - 5, addressV.frame.size.height - 10)];
    addressAlert.text = @"＊(必填)";
    addressAlert.textColor = [UIColor redColor];
    addressAlert.font = [UIFont systemFontOfSize:14.0f];
    [self addSubview:addressAlert];
    
//    邮箱
    UILabel *emailL = [[UILabel alloc] initWithFrame:CGRectMake(addressL.frame.origin.x, addressV.frame.origin.y + addressV.frame.size.height + 5, addressV.frame.size.width, addressV.frame.size.height)];
    emailL.text = @"邮箱";
    emailL.textColor = [UIColor colorWithRed:39 / 255.0 green:56 / 255.0 blue:76 / 255.0 alpha:1.0];
    [self addSubview:emailL];
    
    UIView *emailV = [[UIView alloc] initWithFrame:CGRectMake(addressV.frame.origin.x, emailL.frame.origin.y + emailL.frame.size.height, addressV.frame.size.width, addressV.frame.size.height)];
    emailV.layer.borderColor = [UIColor lightGrayColor].CGColor;
    emailV.layer.borderWidth = 1.0f;
    [self addSubview:emailV];
    
    self.emailForTobeTF = [[UITextField alloc] initWithFrame:CGRectMake(_addressForTobeTF.frame.origin.x, _addressForTobeTF.frame.origin.y, _addressForTobeTF.frame.size.width, _addressForTobeTF.frame.size.height)];
    self.emailForTobeTF.returnKeyType = UIReturnKeyDone;
    [emailV addSubview:_emailForTobeTF];
    
    UILabel *emailAlert = [[UILabel alloc] initWithFrame:CGRectMake(emailV.frame.origin.x + emailV.frame.size.width + 5, emailV.frame.origin.y + 5, WIDTH - 10 - emailV.frame.size.width - 5, emailV.frame.size.height - 10)];
    emailAlert.text = @"＊(必填)";
    emailAlert.textColor = [UIColor redColor];
    emailAlert.font = [UIFont systemFontOfSize:14.0f];
    [self addSubview:emailAlert];
    
    _nameForTobeTF.delegate = self;
    _phoneForTobeTF.delegate = self;
    _addressForTobeTF.delegate = self;
    _emailForTobeTF.delegate = self;
}

//获得子view事件
- (BOOL)pointInside:(CGPoint)point withEvent:(UIEvent *)event {
    
    return YES;
}

#pragma mark -- textfieldDelegate
-(BOOL)textFieldShouldReturn:(UITextField *)textField
{
    [textField resignFirstResponder];
    
    return NO;
}

-(NSString *)saveInfo
{
    if (_nameForTobeTF.text.length == 0 || _phoneForTobeTF.text.length == 0 || _addressForTobeTF.text.length == 0 || _emailForTobeTF.text.length == 0) {
        
        UIAlertView *alertV = [[UIAlertView alloc] initWithTitle:@"提示" message:@"*为必填内容，请确认后再提交" delegate:self cancelButtonTitle:@"取消" otherButtonTitles:@"确认", nil];
        [alertV show];
    }
    
    return [NSString stringWithFormat:@"\"name\":\"%@\",\"phone\":\"%@\",\"address\":\"%@\",\"mail\":\"%@\"", _nameForTobeTF.text, _phoneForTobeTF.text, _addressForTobeTF.text, _emailForTobeTF.text];
}


/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
*/

@end
