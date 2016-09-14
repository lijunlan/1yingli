//
//  TobeTutorForServiceView.h
//  one_mile_01
//
//  Created by 王雅蓉 on 15/10/15.
//  Copyright © 2015年 王雅蓉. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface TobeTutorForServiceView : UIView

@property (nonatomic, strong) UITextField *topicTitleTF;
@property (nonatomic, strong) UITextField *duringTF;
@property (nonatomic, strong) UITextField *priceTF;

@property (nonatomic, strong) UITextView *topicContentTV;
@property (nonatomic, strong) UITextView *selectReasonTV;
@property (nonatomic, strong) UITextView *advantageTV;

-(NSString *)saveServiceInfo;

@end
