//
//  TobeTutorForWorkView.m
//  one_mile_01
//
//  Created by 王雅蓉 on 15/10/15.
//  Copyright © 2015年 王雅蓉. All rights reserved.
//

#import "TobeTutorForWorkView.h"
#define ZHToobarHeight 40

@interface TobeTutorForWorkView ()

@property (nonatomic, copy) NSString *beginTimeForWorkStr;
@property (nonatomic, copy) NSString *endTimeForWorkStr;

@end

@implementation TobeTutorForWorkView

-(instancetype)initWithFrame:(CGRect)frame{
    if (self = [super initWithFrame:frame]) {
        
        [self creatSubView];
        self.toolbar=[[UIToolbar alloc] init];
        self.datePicker=[[UIDatePicker alloc] init];

    }
    return self;
}
-(void)creatSubView{
    UILabel *titleL = [[UILabel alloc]initWithFrame:CGRectMake(10, 0, WIDTH - 10, 35)];
    titleL.text = @"添加工作经历*";
    titleL.font = [UIFont systemFontOfSize:20];
    titleL.textColor = [UIColor colorWithRed:39 / 255.0 green:56 / 255.0 blue:76 / 255.0 alpha:1.0];
    [self addSubview:titleL];
    
    UILabel *noticL = [[UILabel alloc]initWithFrame:CGRectMake(titleL.frame.origin.x, titleL.frame.origin.y + titleL.frame.size.height + 5, titleL.frame.size.width, titleL.frame.size.height)];
    noticL.text = @"* 为必填项，添加之后请保存";
    noticL.textColor = [UIColor colorWithRed:39 / 255.0 green:56 / 255.0 blue:76 / 255.0 alpha:1.0];
    noticL.font = [UIFont systemFontOfSize:16];
    [self addSubview:noticL];
    
    UILabel *schoolL = [[UILabel alloc]initWithFrame:CGRectMake(noticL.frame.origin.x, noticL.frame.origin.y + noticL.frame.size.height + 5, titleL.frame.size.width, titleL.frame.size.height)];
    schoolL.text = @"公司名称 *";
    schoolL.textColor = [UIColor colorWithRed:39 / 255.0 green:56 / 255.0 blue:76 / 255.0 alpha:1.0];
    schoolL.font = [UIFont systemFontOfSize:18];
    [self addSubview:schoolL];
    
    //    学校名称
    self.companyTextF = [[UITextField alloc]initWithFrame:CGRectMake(schoolL.frame.origin.x, schoolL.frame.origin.y + schoolL.frame.size.height + 3, WIDTH - schoolL.frame.origin.x * 2, 35)];
    self.companyTextF.layer.borderWidth = 1;
    self.companyTextF.layer.borderColor = [UIColor lightGrayColor].CGColor;
    self.companyTextF.placeholder = @" 公司名称";
    [self addSubview:self.companyTextF];
    
    UILabel *positionL = [[UILabel alloc]initWithFrame:CGRectMake(self.companyTextF.frame.origin.x, self.companyTextF.frame.origin.y + self.companyTextF.frame.size.height + 5, self.companyTextF.frame.size.width, schoolL.frame.size.height)];
    positionL.textColor = [UIColor colorWithRed:39 / 255.0 green:56 / 255.0 blue:76 / 255.0 alpha:1.0];
    positionL.text = @"所在职位 *";
    [self addSubview:positionL];
    //    学历
    self.positionTextF = [[UITextField alloc]initWithFrame:CGRectMake(self.companyTextF.frame.origin.x, positionL.frame.size.height + positionL.frame.origin.y + 3, self.companyTextF.frame.size.width, self.companyTextF.frame.size.height)];
    self.positionTextF.placeholder = @" 职位";
    self.positionTextF.textColor = [UIColor colorWithRed:39 / 255.0 green:56 / 255.0 blue:76 / 255.0 alpha:1.0];
    self.positionTextF.layer.borderWidth = 1;
    self.positionTextF.layer.borderColor = [UIColor lightGrayColor].CGColor;
    [self addSubview:self.positionTextF];
    
    UILabel *timeL = [[UILabel alloc]initWithFrame:CGRectMake(self.positionTextF.frame.origin.x, self.positionTextF.frame.size.height + self.positionTextF.frame.origin.y + 5, self.positionTextF.frame.size.width, self.positionTextF.frame.size.height)];
    timeL.text = @"起止时间 *";
    timeL.textColor = [UIColor colorWithRed:39 / 255.0 green:56 / 255.0 blue:76 / 255.0 alpha:1.0];
    [self addSubview:timeL];
    
    UILabel *beginTime = [[UILabel alloc]initWithFrame:CGRectMake(timeL.frame.origin.x, timeL.frame.origin.y + timeL.frame.size.height + 5, WIDTH / 5, timeL.frame.size.height)];
    beginTime.text = @"开始时间:";
    if (iPhone4s || iPhone5) {
        beginTime.font = [UIFont systemFontOfSize:13];
    }
    beginTime.textColor = [UIColor colorWithRed:39 / 255.0 green:56 / 255.0 blue:76 / 255.0 alpha:1.0];
    [self addSubview:beginTime];
    self.beginTimeTextF = [[UITextField alloc]initWithFrame:CGRectMake(beginTime.frame.origin.x + beginTime.frame.size.width + 5, beginTime.frame.origin.y, WIDTH / 5, timeL.frame.size.height - 5)];
    if (iPhone4s || iPhone5) {
    self.beginTimeTextF.font = [UIFont systemFontOfSize:13];
    }
    self.beginTimeTextF.layer.borderWidth = 1;
    self.beginTimeTextF.textColor = [UIColor colorWithRed:39 / 255.0 green:56 / 255.0 blue:76 / 255.0 alpha:1.0];
    self.beginTimeTextF.layer.borderColor = [UIColor lightGrayColor].CGColor;
    self.beginTimeTextF.delegate = self;
    [self addSubview:self.beginTimeTextF];
    
    UILabel *lineL = [[UILabel alloc]initWithFrame:CGRectMake(self.beginTimeTextF.frame.origin.x + self.beginTimeTextF.frame.size.width + 5, self.beginTimeTextF.frame.origin.y + self.beginTimeTextF.frame.size.height / 2, WIDTH / 5 / 3 - 5, 1)];
    lineL.textColor = [UIColor colorWithRed:39 / 255.0 green:56 / 255.0 blue:76 / 255.0 alpha:1.0];
    lineL.backgroundColor = [UIColor blackColor];
    [self addSubview:lineL];
    
    UILabel *overTime = [[UILabel alloc]initWithFrame:CGRectMake(lineL.frame.origin.x + lineL.frame.size.width + 5, self.beginTimeTextF.frame.origin.y, WIDTH / 5, timeL.frame.size.height)];
    overTime.text = @"结束时间:";
    if (iPhone4s || iPhone5) {
        overTime.font = [UIFont systemFontOfSize:13];
    }
    overTime.textColor = [UIColor colorWithRed:39 / 255.0 green:56 / 255.0 blue:76 / 255.0 alpha:1.0];
    [self addSubview:overTime];
    
    self.overTimeTextF = [[UITextField alloc]initWithFrame:CGRectMake(overTime.frame.origin.x + overTime.frame.size.width + 5, overTime.frame.origin.y, WIDTH / 5, timeL.frame.size.height - 5)];
    if (iPhone4s || iPhone5) {
        self.overTimeTextF.font = [UIFont systemFontOfSize:13];
    }
    self.overTimeTextF.textColor = [UIColor colorWithRed:39 / 255.0 green:56 / 255.0 blue:76 / 255.0 alpha:1.0];
    self.overTimeTextF.layer.borderWidth = 1;
    self.overTimeTextF.layer.borderColor = [UIColor lightGrayColor].CGColor;
    self.overTimeTextF.delegate = self;
    [self addSubview:self.overTimeTextF];
    
    UILabel *experienceL = [[UILabel alloc]initWithFrame:CGRectMake(timeL.frame.origin.x, beginTime.frame.origin.y + beginTime.frame.size.height + 3, WIDTH - beginTime.frame.origin.x * 2, beginTime.frame.size.height)];
    experienceL.text = @"在校经历";
    experienceL.textColor = [UIColor colorWithRed:39 / 255.0 green:56 / 255.0 blue:76 / 255.0 alpha:1.0];
    [self addSubview:experienceL];
    
    self.experienceTextV = [[UITextView alloc]init];
    self.experienceTextV.delegate = self;
    [self addSubview:self.experienceTextV];
    [self.experienceTextV mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(experienceL.mas_top).offset(experienceL.frame.size.height);
        make.left.equalTo(self.mas_left).offset(10);
        make.right.equalTo(self.mas_right).offset(-10);
        make.height.offset(HEIGHT - experienceL.frame.origin.y - experienceL.frame.size.height - NAVIGATIONHEIGHT - 50);
    }];
    self.experienceTextV.textColor = [UIColor colorWithRed:39 / 255.0 green:56 / 255.0 blue:76 / 255.0 alpha:1.0];
    self.experienceTextV.layer.borderColor = [UIColor lightGrayColor].CGColor;
    self.experienceTextV.layer.borderWidth = 1;
    
    
    UIButton *confirmButton = [UIButton buttonWithType:UIButtonTypeCustom];
    //[self addSubview:confirmButton];
//    [confirmButton mas_makeConstraints:^(MASConstraintMaker *make) {
//        make.top.equalTo(self.experienceTextV.mas_bottom).offset( 10);
//        make.left.equalTo(self.mas_left).offset((WIDTH - 160) / 2);
//        make.height.offset(30);
//        make.width.offset (70);
//    }];
    [confirmButton setTitle:@"保存" forState:UIControlStateNormal];
    confirmButton.backgroundColor = [UIColor colorWithRed:28 /255.0 green:172 / 255.0 blue:248 / 255.0 alpha:1.0];
    
    UIButton *cancelButton = [UIButton buttonWithType:UIButtonTypeCustom];
    //[self addSubview:cancelButton];
//    [cancelButton mas_makeConstraints:^(MASConstraintMaker *make) {
//        make.top.equalTo(self.experienceTextV.mas_bottom).offset(10);
//        make.left.equalTo(confirmButton.mas_right).offset(20);
//        make.height.offset(30);
//        make.width.offset (70);
//    }];
    [cancelButton setTitle:@"取消" forState:UIControlStateNormal];
    cancelButton.backgroundColor = [UIColor lightGrayColor];
    
    UIGestureRecognizer *resignKeyboard = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(backgroundTapped:)];
    [self addGestureRecognizer:resignKeyboard];

}

//撤销键盘
-(void)backgroundTapped:(UITapGestureRecognizer *)tap
{
    [self endEditing:YES];
    [self.datePicker removeFromSuperview];
    [self.toolbar removeFromSuperview];
}

-(void)setUpDatePickerWithdatePickerMode{
    
    self.datePicker.locale = [[NSLocale alloc] initWithLocaleIdentifier:@"zh_CN"];
    self.datePicker.datePickerMode = UIDatePickerModeDate;
    self.datePicker.backgroundColor=[UIColor whiteColor];

    self.datePicker.frame=CGRectMake(0,HEIGHT - 280 , WIDTH, 217);
    [self addSubview:self.datePicker];
    
    [self setUpToolBar];
    
}

#pragma mark -- textFieldDelegate
-(void)textFieldDidBeginEditing:(UITextField *)textField
{
    
    if (textField == self.beginTimeTextF) {
        self.tagTobeForEdu = @"10001";
        [self setUpDatePickerWithdatePickerMode];
        [self endEditing:YES];
        
    }else if (textField == self.overTimeTextF){
        self.tagTobeForEdu = @"10002";
        [self setUpDatePickerWithdatePickerMode];
        [self endEditing:YES];
        
    }
    
}
#pragma mark -- textViewDelegate
-(void)textViewDidBeginEditing:(UITextView *)textView{
    
    [UIView beginAnimations:@"ResizeForKeyboard" context:nil];
    [UIView setAnimationDuration:0.3f];
    
    self.frame = CGRectMake(0, -200, WIDTH, HEIGHT);
    
    [UIView commitAnimations];
    
}

-(void)textViewDidEndEditing:(UITextView *)textView{
    [UIView beginAnimations:@"ResizeForKeyboard" context:nil];
    [UIView setAnimationDuration:0.3f];
    
    self.frame = CGRectMake(0, NAVIGATIONHEIGHT, WIDTH, HEIGHT);
    
    [UIView commitAnimations];
}




-(void)setUpToolBar{
    
    _toolbar = [self setToolbarStyle];
    [self setToolbarWithPickViewFrame];
    [self addSubview:_toolbar];
    
}

-(UIToolbar *)setToolbarStyle{
    
    UIBarButtonItem *lefttem=[[UIBarButtonItem alloc] initWithTitle:@"取消" style:UIBarButtonItemStyleDone target:self action:@selector(removeView)];
    
    UIBarButtonItem *centerSpace=[[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemFlexibleSpace target:self action:nil];
    
    UIBarButtonItem *right=[[UIBarButtonItem alloc] initWithTitle:@"确定" style:UIBarButtonItemStyleDone target:self action:@selector(doneClick)];
    
    self.toolbar.items=@[lefttem,centerSpace,right];
    
    return self.toolbar;
}

-(void)setToolbarWithPickViewFrame{
    
    _toolbar.frame=CGRectMake(0, HEIGHT - 280 - ZHToobarHeight ,[UIScreen mainScreen].bounds.size.width, ZHToobarHeight);
    
    _toolbar.backgroundColor = [UIColor colorWithRed:237 / 255.0  green:237 / 255.0 blue:237 / 255.0 alpha:1.0];
}

//取消
-(void)removeView{
    
    [self.datePicker removeFromSuperview];
    [self.toolbar removeFromSuperview];
}
//确认
-(void)doneClick
{
    //日期转字符串
    NSDateFormatter *dateFormatter = [[NSDateFormatter alloc] init];
    //设置日期格式
    dateFormatter.dateFormat = @" yyyy-MM";
    NSString *dateStr =  [dateFormatter stringFromDate:self.datePicker.date];
    
    if ([self.tagTobeForEdu isEqualToString: @"10001"]) {
        self.beginTimeTextF.text = dateStr;
        self.beginTimeForWorkStr = [NSString stringWithFormat:@"%@,%@", [dateStr substringWithRange:NSMakeRange(1, 4)], [dateStr substringWithRange:NSMakeRange(6, 2)]];
    }else if ([self.tagTobeForEdu isEqualToString:@"10002"]){
        self.overTimeTextF.text = dateStr;
        self.endTimeForWorkStr = [NSString stringWithFormat:@"%@,%@", [dateStr substringWithRange:NSMakeRange(1, 4)], [dateStr substringWithRange:NSMakeRange(6, 2)]];
    }
    [self.datePicker removeFromSuperview];
    [self.toolbar removeFromSuperview];
}

-(NSString *)saveWorkInfo
{
    return [NSString stringWithFormat:@"[{\"startTime\":\"%@\",\"position\":\"%@\",\"description\":\"%@\",\"endTime\":\"%@\",\"companyName\":\"%@\"}]", self.beginTimeForWorkStr, _positionTextF.text, _experienceTextV.text, self.endTimeForWorkStr, _companyTextF.text];
}

@end
