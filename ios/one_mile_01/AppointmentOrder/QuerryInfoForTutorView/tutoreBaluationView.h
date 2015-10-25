//
//  tutoreBaluationView.h
//  one_mile_01
//
//  Created by 王进帅 on 15/9/17.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import <UIKit/UIKit.h>

@protocol cancelTutorBaluation <NSObject>

-(void)cancelTutorBaluation;

@end

@interface tutoreBaluationView : UIView

@property(nonatomic,assign)id<cancelTutorBaluation>mydelegate;
@property(nonatomic,copy)NSString *tutorBaluationOrderID;
@property(nonatomic,strong)UITextView *textView ;
@property(nonatomic,assign)NSInteger starNum;
@property(nonatomic,strong)UIButton *baluationStarButton;
@property(nonatomic,strong)UIView *settingView;
@property(nonatomic,strong)NSMutableArray *buttonArray;
@end
