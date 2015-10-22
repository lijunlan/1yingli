//
//  TobeTutorForEduView.h
//  one_mile_01
//
//  Created by 王雅蓉 on 15/10/15.
//  Copyright © 2015年 王雅蓉. All rights reserved.
//

#import <UIKit/UIKit.h>


@interface TobeTutorForEduView : UIView<UITextFieldDelegate,UITextViewDelegate>

@property (nonatomic,strong)UITextField *schoolTextF;//学校
@property (nonatomic,strong)UITextField *academicTextF;//学历
@property (nonatomic,strong)UITextView *experienceTextV;//经历
@property (nonatomic, strong) UITextField *professionalTextF;//专业
@property (strong,nonatomic) UITextField *beginTimeTextF;// 开始时间
@property (strong,nonatomic) UITextField *overTimeTextF; // 结束时间

@property(nonatomic,strong)UIDatePicker *datePicker;//时间选择
@property(nonatomic,copy)NSString *resultString;//最终时间字符串
@property(nonatomic,strong)UIToolbar *toolbar;//工具栏
@property (nonatomic,copy)NSString *tagTobeForEdu;//标记

-(NSString *)saveEduInfo;

@end
