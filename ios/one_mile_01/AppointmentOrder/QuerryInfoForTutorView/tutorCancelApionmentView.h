//
//  tutorCancelApionmentView.h
//  one_mile_01
//
//  Created by 王进帅 on 15/9/18.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import <UIKit/UIKit.h>

@protocol tutorCancelAppionment <NSObject>

-(void)tutorCancelAppionmentMethod;

@end

@interface tutorCancelApionmentView : UIView

@property(nonatomic,assign)id<tutorCancelAppionment>mydelegate;
@property(nonatomic,strong)UITextView *textView ;
@property(nonatomic,copy)NSString *tutorCancelApionmentOrderID;
@end
