//
//  refuseAceptOrderView.h
//  one_mile_01
//
//  Created by 王进帅 on 15/9/24.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import <UIKit/UIKit.h>

@protocol refuseAceptOrder <NSObject>

-(void)refuseAceptOrder;

@end

@interface refuseAceptOrderView : UIView
@property(nonatomic,strong)UITextView *textView ;
@property(nonatomic,strong)UIView *settingView;
@property(nonatomic,copy)NSString *refuseAceptOrderID;

@property(nonatomic,assign)id<refuseAceptOrder>mydelegate;

@end
