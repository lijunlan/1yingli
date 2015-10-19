//
//  AppointmentOrderViewController.h
//  one_mile_01
//
//  Created by 王雅蓉 on 15/8/26.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import <UIKit/UIKit.h>

#import "QuerryInfoViewController.h"
#import "QuerryInfoView.h"
#import "TutorHomeModal.h"

@interface AppointmentOrderViewController : UIViewController<querryEvent,UIScrollViewDelegate,UITextViewDelegate>

@property (nonatomic,strong)UIScrollView *scrollView;
@property (nonatomic,strong)UIImageView *imageView3;
@property (nonatomic, strong) UILabel *label3; //200元/次
@property (nonatomic, copy) NSString *pricePertimes;
@property (nonatomic, copy) NSString *appointTopic;
@property (nonatomic, copy) NSString *teacherIDForOrder;
@property (nonatomic, copy) NSString *contactWay;

@property (nonatomic, strong) TutorHomeModal *tutorForAppointment;

@end
