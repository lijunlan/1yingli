//
//  agreeReturnMoneyView.h
//  one_mile_01
//
//  Created by 王进帅 on 15/9/17.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import <UIKit/UIKit.h>

@protocol cancelAgreeReturnMoney <NSObject>

-(void)cancelAgreeReturnM;

@end

@interface agreeReturnMoneyView : UIView

@property (nonatomic,assign)id<cancelAgreeReturnMoney>mydelegate;
@property (nonatomic,copy)NSString *AgreeRturnMoneyOrderID;
@end
