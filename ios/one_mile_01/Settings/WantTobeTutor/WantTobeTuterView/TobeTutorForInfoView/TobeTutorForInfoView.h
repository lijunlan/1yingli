//
//  TobeTutorForInfoView.h
//  one_mile_01
//
//  Created by 王雅蓉 on 15/10/15.
//  Copyright © 2015年 王雅蓉. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface TobeTutorForInfoView : UIView<UITextFieldDelegate>

@property (nonatomic, strong) UITextField *nameForTobeTF;
@property (nonatomic, strong) UITextField *phoneForTobeTF;
@property (nonatomic, strong) UITextField *addressForTobeTF;
@property (nonatomic, strong) UITextField *emailForTobeTF;

-(NSString *)saveInfo;

@end
