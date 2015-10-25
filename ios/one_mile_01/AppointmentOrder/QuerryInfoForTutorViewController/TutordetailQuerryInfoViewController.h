//
//  TutordetailQuerryInfoViewController.h
//  one_mile_01
//
//  Created by 王进帅 on 15/9/17.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "agreeReturnMoneyView.h"
#import "refuseReturnMoneyView.h"
#import "tutoreBaluationView.h"
#import "tutorProductOrderModel.h"
#import "tutorCancelApionmentView.h"
#import "refuseAceptOrderView.h"

@interface TutordetailQuerryInfoViewController : UIViewController<cancelAgreeReturnMoney,cancelRefuseReturnMoney,cancelTutorBaluation,UITextFieldDelegate,tutorCancelAppionment,refuseAceptOrder>
@property (nonatomic,assign)NSInteger tutorTagForColor;
@property (nonatomic,strong)agreeReturnMoneyView *agreeReturnMoneyV;
@property (nonatomic,strong)refuseReturnMoneyView *refuseRturnMoneyV;
@property (nonatomic,strong)tutoreBaluationView *tutorBaluatioV;
@property (nonatomic,strong)tutorCancelApionmentView *tutorCancelAppionmentV;
@property (nonatomic,strong)refuseAceptOrderView *refuseAceptV;
@property (nonatomic,copy)NSString  *tutorDetailOrderS;

@property (nonatomic,copy)NSString *orderID;

@end
