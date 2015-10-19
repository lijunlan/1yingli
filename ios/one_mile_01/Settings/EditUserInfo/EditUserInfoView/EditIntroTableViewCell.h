//
//  EditIntroTableViewCell.h
//  one_mile_01
//
//  Created by 王雅蓉 on 15/8/27.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import <UIKit/UIKit.h>

@protocol EditIntroInterface <NSObject>

@optional
-(void)beginEdit;
-(void)endEdit:(NSString *)intro;

@end

@interface EditIntroTableViewCell : UITableViewCell<UITextViewDelegate>

@property (nonatomic, strong) UITextView *introTV;
@property (nonatomic, assign) id<EditIntroInterface>myDelegate;

@end
