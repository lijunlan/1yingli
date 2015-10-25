//
//  refuseReturnMoneyView.h
//  one_mile_01
//
//  Created by 王进帅 on 15/9/17.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import <UIKit/UIKit.h>

@protocol cancelRefuseReturnMoney <NSObject>

-(void)cancelRefuseReturnMoney;

@end

@interface refuseReturnMoneyView : UIView

@property(nonatomic,assign)id<cancelRefuseReturnMoney>mydelegate;
@property(nonatomic,copy)NSString *refuseRetrunMoneyID;
@end
