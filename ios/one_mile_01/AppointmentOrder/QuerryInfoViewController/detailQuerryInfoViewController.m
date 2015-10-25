//
//  detailQuerryInfoViewController.m
//  one_mile_01
//
//  Created by 王进帅 on 15/9/16.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import "detailQuerryInfoViewController.h"
#import "Order.h"
#import "DataSigner.h"
#import "RSADataSigner.h"
#import <AlipaySDK/AlipaySDK.h>
#import "QueryProduct.h"
#import "detailCancelConsultView.h"
#import "judgementView.h"

@interface detailQuerryInfoViewController ()<UIAlertViewDelegate, cancelConsultInterface, judgementInterface>

@property(nonatomic,strong)UILabel *oneLabel;
@property(nonatomic,strong)UILabel *oneLabelLine;
@property(nonatomic,strong)UILabel *applyLabel;

@property(nonatomic,strong) UILabel *twolabel;
@property(nonatomic,strong) UILabel *twoLabelLine;
@property(nonatomic,strong) UILabel *confirmLabel;

@property(nonatomic,strong) UILabel *threeLabel;
@property(nonatomic,strong) UILabel *threeLabelLine;
@property(nonatomic,strong) UILabel *consultLabel;

@property(nonatomic,strong) UILabel *fourlabel;
@property(nonatomic,strong) UILabel *fourLabelLine;
@property(nonatomic,strong) UILabel *severLabel;

@property(nonatomic,strong) UILabel *fiveLabel;
@property(nonatomic,strong) UILabel *judgeLabel;

@property(nonatomic,strong) UILabel *contentTitleLabel;
@property(nonatomic,strong) UILabel *contentLabel;

@property(nonatomic,strong) UIButton *inspectConsultbutton;
@property(nonatomic,strong) UIButton *QuerryCancelbutton;
@property(nonatomic,strong) UIButton *payButton;

@property (nonatomic, strong) detailCancelConsultView *detailCancelConsultV;
@property (nonatomic, strong) QueryProduct *product;
@property (nonatomic, strong) judgementView *judgeForServiceV;

@end

@implementation detailQuerryInfoViewController

-(instancetype)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    if (self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil]) {
        
        self.product = [[QueryProduct alloc] init];
    }
    
    return self;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    self.view.backgroundColor = [UIColor whiteColor];
    //    返回按钮
    UIButton *button = [UIButton buttonWithType:UIButtonTypeCustom];
    button.frame = CGRectMake(20, 40, PUSHANDPOPBUTTONSIZE, PUSHANDPOPBUTTONSIZE);
    [button setBackgroundImage:[UIImage imageNamed:@"pop_dark.png"] forState:UIControlStateNormal];
    button.backgroundColor = [UIColor clearColor];
    [button addTarget:self action:@selector(detailQuerryInfoBackbutton:) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:button];
    
    //订单详情
    self.navigationController.navigationBar.hidden = YES;
    UILabel *titleLabel = [[UILabel alloc]initWithFrame:CGRectMake(self.view.frame.size.width / 2 - 50, button.frame.origin.y,100, 30)];
    titleLabel.text = @"订单详情";
    titleLabel.font = [UIFont systemFontOfSize:23];
    titleLabel.textColor = [UIColor lightGrayColor];
    [self.view addSubview:titleLabel];
    
    self.contentTitleLabel = [[UILabel alloc]initWithFrame:CGRectMake(30, button.frame.origin.y + button.frame.size.height + 30, self.view.frame.size.width - 60, 50)];
    self.contentTitleLabel.numberOfLines = 2;
    _contentTitleLabel.font = [UIFont systemFontOfSize:20];
    _contentTitleLabel.textAlignment = NSTextAlignmentCenter;
    _contentTitleLabel.textColor = [UIColor lightGrayColor];
    [self.view addSubview:_contentTitleLabel];
    
    self.contentLabel = [[UILabel alloc]initWithFrame:CGRectMake(10, _contentTitleLabel.frame.origin.y + _contentTitleLabel.frame.size.height + 10, self.view.frame.size.width - 20,_contentTitleLabel.frame.size.height + 30)];
    _contentLabel.numberOfLines = 2;
    _contentLabel.font = [UIFont systemFontOfSize:16];
    _contentLabel.textAlignment = NSTextAlignmentCenter;
    _contentLabel.textColor = [UIColor lightGrayColor];
    [self.view addSubview:_contentLabel];
    
    
    //    小数字
    self.oneLabel = [[UILabel alloc]initWithFrame:CGRectMake(self.view.frame.origin.x + 30 ,_contentLabel.frame.origin.y + _contentLabel.frame.size.height + 40,15, 15)];
    self.oneLabel.layer.masksToBounds = YES;
    self.oneLabel.layer.cornerRadius = 7;
    self.oneLabel.text = @"1";
    self.oneLabel.font = [UIFont systemFontOfSize:10];
    self.oneLabel.textAlignment = NSTextAlignmentCenter;
    self.oneLabel.textColor = [UIColor whiteColor];
    self.oneLabel.backgroundColor = [UIColor lightGrayColor];
    [self.view addSubview:self.oneLabel];
    
    //蓝线
    self.oneLabelLine = [[UILabel alloc]init];
    if (iPhone6) {
        self.oneLabelLine.frame = CGRectMake(_oneLabel.frame.origin.x + _oneLabel.frame.size.width, _oneLabel.frame.origin.y + 7 ,60, 1);
    }else if (iPhone5){
        self.oneLabelLine.frame = CGRectMake(_oneLabel.frame.origin.x + _oneLabel.frame.size.width, _oneLabel.frame.origin.y + 7 ,45, 1);
    }else if (iPhone6P){
        self.oneLabelLine.frame = CGRectMake(_oneLabel.frame.origin.x + _oneLabel.frame.size.width, _oneLabel.frame.origin.y + 7 ,70, 1);

    }
    
    _oneLabelLine.backgroundColor = [UIColor lightGrayColor];
    [self.view addSubview:_oneLabelLine];
    
    self.applyLabel = [[UILabel alloc]initWithFrame:CGRectMake(_oneLabel.frame.origin.x -12, _oneLabel.frame.origin.y + _oneLabel.frame.size.height, 80, 20)];
    _applyLabel.backgroundColor = [UIColor clearColor];
    _applyLabel.text =@"学员申请";
    _applyLabel.textColor = [UIColor lightGrayColor];
    _applyLabel.font = [UIFont systemFontOfSize:10];
    [self.view addSubview:_applyLabel];
    
    
    //    导师确认
    //    小数字
     self.twolabel = [[UILabel alloc]initWithFrame:CGRectMake(_oneLabelLine.frame.origin.x + _oneLabelLine.frame.size.width ,_oneLabel.frame.origin.y,15, 15)];
    _twolabel.layer.masksToBounds = YES;
    _twolabel.layer.cornerRadius = 7;
    _twolabel.text = @"2";
    _twolabel.font = [UIFont systemFontOfSize:10];
    _twolabel.textAlignment = NSTextAlignmentCenter;
    _twolabel.textColor = [UIColor whiteColor];
    _twolabel.backgroundColor = [UIColor lightGrayColor];
    [self.view addSubview:_twolabel];
    
    //蓝线
    _twoLabelLine = [[UILabel alloc]initWithFrame:CGRectMake(_twolabel.frame.origin.x + _twolabel.frame.size.width, _twolabel.frame.origin.y + 7 ,_oneLabelLine.frame.size.width, 1)];
    _twoLabelLine.backgroundColor = [UIColor lightGrayColor];
    [self.view addSubview:_twoLabelLine];
    

    self.confirmLabel = [[UILabel alloc]initWithFrame:CGRectMake(_twolabel.frame.origin.x - 12, _twolabel.frame.origin.y + _twolabel.frame.size.height, 80, 20)];
    _confirmLabel.backgroundColor = [UIColor clearColor];
    _confirmLabel.text =@"导师确认";
    _confirmLabel.textColor = [UIColor lightGrayColor];
    _confirmLabel.font = [UIFont systemFontOfSize:10];
    [self.view addSubview:_confirmLabel];
    
    
    //    协商时间
    //    小数字
    self.threeLabel = [[UILabel alloc]initWithFrame:CGRectMake(_twoLabelLine.frame.origin.x + _twoLabelLine.frame.size.width ,_oneLabel.frame.origin.y,15, 15)];
    _threeLabel.layer.masksToBounds = YES;
    _threeLabel.layer.cornerRadius = 7;
    _threeLabel.text = @"3";
    _threeLabel.font = [UIFont systemFontOfSize:10];
    _threeLabel.textAlignment = NSTextAlignmentCenter;
    _threeLabel.textColor = [UIColor whiteColor];
    _threeLabel.backgroundColor = [UIColor lightGrayColor];
    [self.view addSubview:_threeLabel];
    
    //蓝线
    _threeLabelLine = [[UILabel alloc]initWithFrame:CGRectMake(_threeLabel.frame.origin.x + _threeLabel.frame.size.width, _threeLabel.frame.origin.y + 7 ,_oneLabelLine.frame.size.width, 1)];
    _threeLabelLine.backgroundColor = [UIColor lightGrayColor];
    [self.view addSubview:_threeLabelLine];
    
    _consultLabel = [[UILabel alloc]initWithFrame:CGRectMake(_threeLabel.frame.origin.x -12, _threeLabel.frame.origin.y + _threeLabel.frame.size.height, 80, 20)];
    _consultLabel.backgroundColor = [UIColor clearColor];
    _consultLabel.text =@"协商时间";
    _consultLabel.textColor = [UIColor lightGrayColor];
    _consultLabel.font = [UIFont systemFontOfSize:10];
    [self.view addSubview:_consultLabel];
    
    
    //    服务进行
    //    小数字
    self.fourlabel = [[UILabel alloc]initWithFrame:CGRectMake(_threeLabelLine.frame.origin.x + _threeLabelLine.frame.size.width ,_oneLabel.frame.origin.y,15, 15)];
    _fourlabel.layer.masksToBounds = YES;
    _fourlabel.layer.cornerRadius = 7;
    _fourlabel.text = @"4";
    _fourlabel.font = [UIFont systemFontOfSize:10];
    _fourlabel.textAlignment = NSTextAlignmentCenter;
    _fourlabel.textColor = [UIColor whiteColor];
    _fourlabel.backgroundColor = [UIColor lightGrayColor];
    [self.view addSubview:_fourlabel];
    
    //蓝线
    _fourLabelLine = [[UILabel alloc]initWithFrame:CGRectMake(_fourlabel.frame.origin.x + _fourlabel.frame.size.width, _fourlabel.frame.origin.y + 7 ,_oneLabelLine.frame.size.width, 1)];
    _fourLabelLine.backgroundColor = [UIColor lightGrayColor];
    [self.view addSubview:_fourLabelLine];
    
    _severLabel = [[UILabel alloc]initWithFrame:CGRectMake(_fourlabel.frame.origin.x -12, _fourlabel.frame.origin.y + _fourlabel.frame.size.height, 80, 20)];
    _severLabel.backgroundColor = [UIColor clearColor];
    _severLabel.text =@"服务进行";
    _severLabel.textColor = [UIColor lightGrayColor];
    _severLabel.font = [UIFont systemFontOfSize:10];
    [self.view addSubview:_severLabel];
    
    
    //    双方评价
    //    小数字
    _fiveLabel = [[UILabel alloc]initWithFrame:CGRectMake(_fourLabelLine.frame.origin.x + _fourLabelLine.frame.size.width ,_oneLabel.frame.origin.y,15, 15)];
    _fiveLabel.layer.masksToBounds = YES;
    _fiveLabel.layer.cornerRadius = 7;
    _fiveLabel.text = @"5";
    _fiveLabel.font = [UIFont systemFontOfSize:10];
    _fiveLabel.textAlignment = NSTextAlignmentCenter;
    _fiveLabel.textColor = [UIColor whiteColor];
    _fiveLabel.backgroundColor = [UIColor lightGrayColor];
    [self.view addSubview:_fiveLabel];

    _judgeLabel = [[UILabel alloc]initWithFrame:CGRectMake(_fiveLabel.frame.origin.x -12, _fiveLabel.frame.origin.y + _fiveLabel.frame.size.height, 80, 20)];
    _judgeLabel.backgroundColor = [UIColor clearColor];
    _judgeLabel.text =@"双方评价";
    _judgeLabel.textColor = [UIColor lightGrayColor];
    _judgeLabel.font = [UIFont systemFontOfSize:10];
    [self.view addSubview:_judgeLabel];
    
//    取消服务按钮
    self.QuerryCancelbutton = [[UIButton alloc]initWithFrame:CGRectMake(120, _contentLabel.frame.origin.y + _contentLabel.frame.size.height + 140, self.view.frame.size.width - 240, 40)];
    [_QuerryCancelbutton setTintColor:[UIColor whiteColor]];

    
    _QuerryCancelbutton.backgroundColor = [UIColor colorWithRed:73/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
    [self.QuerryCancelbutton addTarget:self action:@selector(QuerryCancelbuttonAction:) forControlEvents:UIControlEventTouchUpInside];
    _QuerryCancelbutton.layer.masksToBounds = YES;
    _QuerryCancelbutton.layer.cornerRadius = 20;
    [self.view addSubview:_QuerryCancelbutton];
    
    
    self.payButton = [UIButton buttonWithType:UIButtonTypeCustom];
    _payButton.layer.masksToBounds = YES;
    _payButton.layer.cornerRadius = 20;
    _payButton.backgroundColor = [UIColor colorWithRed:73/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
    [_payButton addTarget:self action:@selector(payOrCommentsAction:) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:_payButton];
    
    //    查看资讯内容按钮
    self.inspectConsultbutton = [UIButton buttonWithType:UIButtonTypeCustom];
    [self.view addSubview:self.inspectConsultbutton];
    [self.inspectConsultbutton mas_makeConstraints:^(MASConstraintMaker *make) {
        make.bottom.equalTo(self.view.mas_bottom).offset(-100);
        make.left.equalTo(self.view.mas_left).offset(WIDTH / 2 - 70);
        make.right.equalTo(self.view.mas_right).offset(-(WIDTH / 2 - 70 ));
        make.top.equalTo(self.view.mas_top).offset(HEIGHT - 100 - 40);
    }];
    [self.inspectConsultbutton setTitleColor:[UIColor colorWithRed:73/255.0 green:173/255.0 blue:227/255.0 alpha:1.0] forState:UIControlStateNormal];
    [self.inspectConsultbutton addTarget:self action:@selector(inspectConsultButtonAction:) forControlEvents:UIControlEventTouchUpInside];
    
    if (self.tagforColor == 0) { //申请
        
        if ([[self.orderForDetailM.state substringToIndex:4] isEqualToString:@"0200"]) {
            
            _contentTitleLabel.text = @"服务终止";
            _contentLabel.text = @"您已取消服务";
            
            _payButton.frame = CGRectZero;
            _QuerryCancelbutton.frame = CGRectZero;
            
            [self.inspectConsultbutton mas_makeConstraints:^(MASConstraintMaker *make) {
                make.bottom.equalTo(self.view.mas_bottom).offset(0);
                make.left.equalTo(self.view.mas_left).offset(0);
                make.right.equalTo(self.view.mas_right).offset(-(WIDTH));
                make.top.equalTo(self.view.mas_top).offset(HEIGHT);
            }];

        } else if ([[self.orderForDetailM.state substringToIndex:4] isEqualToString:@"0700"]) {
            
            _contentTitleLabel.text = @"服务终止";
            _contentLabel.text = @"耐心等待，正在处理订单。\n如有疑问，请拨打电话0571—886415101";
            
            _payButton.frame = CGRectZero;
            _QuerryCancelbutton.frame = CGRectZero;
        } else if ([[self.orderForDetailM.state substringToIndex:4] isEqualToString:@"0800"]) {
        
        self.payButton = [UIButton buttonWithType:UIButtonTypeCustom];
        self.payButton.frame = CGRectMake(_QuerryCancelbutton.frame.origin.x + _QuerryCancelbutton.frame.size.width + 40,  _contentLabel.frame.origin.y + _contentLabel.frame.size.height + 140, _QuerryCancelbutton.frame.size.width, self.QuerryCancelbutton.frame.size.height);
        _payButton.layer.masksToBounds = YES;
        _payButton.layer.cornerRadius = 20;
        _payButton.backgroundColor = [UIColor colorWithRed:73/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        [_payButton setTitle:@"支付" forState:UIControlStateNormal];

        [self.view addSubview:_payButton];
            _contentTitleLabel.text = @"服务终止";
            _contentLabel.text = @"退款成功，请注意查收。\n如有疑问，请拨打电话0571—886415101";
            
            _payButton.frame = CGRectZero;
            _QuerryCancelbutton.frame = CGRectZero;
            [self.inspectConsultbutton mas_makeConstraints:^(MASConstraintMaker *make) {
                make.bottom.equalTo(self.view.mas_bottom).offset(0);
                make.left.equalTo(self.view.mas_left).offset(0);
                make.right.equalTo(self.view.mas_right).offset(-(WIDTH));
                make.top.equalTo(self.view.mas_top).offset(HEIGHT);
            }];

        } else if ([[self.orderForDetailM.state substringToIndex:4] isEqualToString:@"0100"]) {
        
            _contentTitleLabel.text = @"您未完成支付，请完成支付操作";
            _contentLabel.text = @"完成支付后，请等待导师确认。";
            
            _QuerryCancelbutton.frame = CGRectMake(30, _contentLabel.frame.origin.y + _contentLabel.frame.size.height + 140, self.view.frame.size.width - 240, 40);
            [_QuerryCancelbutton setTitle:@"取消订单" forState:UIControlStateNormal];
            
            [_inspectConsultbutton setTitle:@"查看咨询内容" forState:UIControlStateNormal];

            self.payButton.frame = CGRectMake(_QuerryCancelbutton.frame.origin.x + _QuerryCancelbutton.frame.size.width + 40, _QuerryCancelbutton.frame.origin.y, _QuerryCancelbutton.frame.size.width, self.QuerryCancelbutton.frame.size.height);
            [_payButton setTitle:@"支付" forState:UIControlStateNormal];
            
        } else {
        
            _contentTitleLabel.text = @"服务终止";
            _contentLabel.text = @"订单已取消";
            
            _payButton.frame = CGRectZero;
            _QuerryCancelbutton.frame = CGRectZero;
            [self.inspectConsultbutton mas_makeConstraints:^(MASConstraintMaker *make) {
                make.bottom.equalTo(self.view.mas_bottom).offset(0);
                make.left.equalTo(self.view.mas_left).offset(0);
                make.right.equalTo(self.view.mas_right).offset(-(WIDTH));
                make.top.equalTo(self.view.mas_top).offset(HEIGHT);
            }];

        }
        
    } else if (self.tagforColor == 1) { //已完成支付
        
        if ([[self.orderForDetailM.state substringToIndex:4] isEqualToString:@"0300"]) {
            
            _contentTitleLabel.text = @"您已完成支付，等待导师确认";
            _contentLabel.text = @"若导师拒绝，您的咨询费用会立即退回到您的支付宝。";
        } else {
        
            _contentTitleLabel.text = @"您已取消了本次服务";
            _contentLabel.text = @"正在处理订单，会及时将咨询费用退回到您的支付宝";
        }
        [_inspectConsultbutton setTitle:@"查看咨询内容" forState:UIControlStateNormal];
        
        _QuerryCancelbutton.frame = CGRectMake(WIDTH / 2 - 80, _contentLabel.frame.origin.y + _contentLabel.frame.size.height + 160, 160, 40);
        [_QuerryCancelbutton setTitle:@"取消服务" forState:UIControlStateNormal];
        _payButton.frame = CGRectZero;
        
        self.oneLabel.backgroundColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        _oneLabelLine.backgroundColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        _applyLabel.textColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];

    }else if (self.tagforColor == 2){ //已确认
        
        if ([[self.orderForDetailM.state substringToIndex:4] isEqualToString:@"0400"]) {
            
            _contentTitleLabel.text = @"您的导师已确认，等待导师联系";
            _contentLabel.text = @"导师会在24小时联系您，请保持电话通畅。";
        } else if ([[self.orderForDetailM.state substringToIndex:4] isEqualToString:@"1500"]) {
        
            _contentTitleLabel.text = @"您已取消了本次服务";
            _contentLabel.text = @"正在处理订单，耐心等待导师的回应";
        } else if ([[self.orderForDetailM.state substringToIndex:4] isEqualToString:@"0700"]) {
        
            _contentTitleLabel.text = @"您已取消了本次服务";
            _contentLabel.text = @"导师同意退款，我们会及时将咨询费用退回到您的支付宝";
        } else {
        
            _contentTitleLabel.text = @"您已取消了本次服务";
            _contentLabel.text = @"导师不同意退款，客服介入，订单正在处理中";
        }
        [_inspectConsultbutton setTitle:@"查看咨询内容" forState:UIControlStateNormal];
        
        _QuerryCancelbutton.frame = CGRectMake(WIDTH / 2 - 80, _contentLabel.frame.origin.y + _contentLabel.frame.size.height + 140 , 160, 40);
        [_QuerryCancelbutton setTitle:@"取消咨询" forState:UIControlStateNormal];
        _QuerryCancelbutton.tag = 10050;
        _payButton.frame = CGRectZero;
        
        self.oneLabel.backgroundColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        _oneLabelLine.backgroundColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        _applyLabel.textColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        self.twolabel.backgroundColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        _twoLabelLine.backgroundColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        _confirmLabel.textColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
    }else if (self.tagforColor == 3){  //确定时间
        
        if ([[self.orderForDetailM.state substringToIndex:4] isEqualToString:@"0500"]) {
            
            _contentTitleLabel.text = @"双方协商时间，等待咨询";
            _contentLabel.text = [NSString stringWithFormat:@"导师已经和您联系，请按照约定时间(%@)等待咨询。", _orderForDetailM.okTime];
        } else if ([[self.orderForDetailM.state substringToIndex:4] isEqualToString:@"1500"]) {
        
            _contentTitleLabel.text = @"您已取消咨询";
            _contentLabel.text = @"正在处理订单，耐心等待导师的回应";
        } else if ([[self.orderForDetailM.state substringToIndex:4] isEqualToString:@"0700"]) {
        
            _contentTitleLabel.text = @"您已取消咨询";
            _contentLabel.text = @"导师同意退款，我们会及时将咨询费用退回到您的支付宝";
        } else {
        
            _contentTitleLabel.text = @"您已取消咨询";
            _contentLabel.text = @"导师不同意退款，客服介入，订单正在处理中";
        }
        [_inspectConsultbutton setTitle:@"查看咨询内容" forState:UIControlStateNormal];
        
        _QuerryCancelbutton.frame = CGRectMake(WIDTH / 2 - 80, _contentLabel.frame.origin.y + _contentLabel.frame.size.height + 140, 160, 40);
        [_QuerryCancelbutton setTitle:@"取消咨询" forState:UIControlStateNormal];
        _QuerryCancelbutton.tag = 10051;
        _payButton.frame = CGRectZero;
        
        [self.inspectConsultbutton mas_makeConstraints:^(MASConstraintMaker *make) {
            make.bottom.equalTo(self.view.mas_bottom).offset(0);
            make.left.equalTo(self.view.mas_left).offset(0);
            make.right.equalTo(self.view.mas_right).offset(-(WIDTH));
            make.top.equalTo(self.view.mas_top).offset(HEIGHT);
        }];

        self.oneLabel.backgroundColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        _oneLabelLine.backgroundColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        _applyLabel.textColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        self.twolabel.backgroundColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        _twoLabelLine.backgroundColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        _confirmLabel.textColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        self.threeLabel.backgroundColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        _threeLabelLine.backgroundColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        _consultLabel.textColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
    }else if (self.tagforColor == 4){ //咨询中
        
        [_inspectConsultbutton setTitle:@"查看咨询内容" forState:UIControlStateNormal];
        
        if ([[self.orderForDetailM.state substringToIndex:4] isEqualToString:@"0500"]) {
            
            _contentTitleLabel.text = @"咨询中";
            _contentLabel.text = @"请按照约定时间进行咨询。";
            
            _QuerryCancelbutton.frame = CGRectMake(10 , _contentLabel.frame.origin.y + _contentLabel.frame.size.height + 140, (WIDTH - 40) / 3 , 40);
            [_QuerryCancelbutton setTitle:@"不满意" forState:UIControlStateNormal];
            _QuerryCancelbutton.tag = 10052;
            
            self.payButton.frame = CGRectMake( (WIDTH - 40) / 3 * 2 + 10 , _QuerryCancelbutton.frame.origin.y, _QuerryCancelbutton.frame.size.width, self.QuerryCancelbutton.frame.size.height);
            [self.payButton setTitle:@"满意" forState:UIControlStateNormal];
        } else if ([[self.orderForDetailM.state substringToIndex:4] isEqualToString:@"1000"]) {
        
            _contentTitleLabel.text = @"服务完成";
            _contentLabel.text = @"等待双方评价";

            _payButton.frame = CGRectZero;
            _QuerryCancelbutton.frame = CGRectZero;
        } else if ([[self.orderForDetailM.state substringToIndex:4] isEqualToString:@"0620"]) {
        
            _contentTitleLabel.text = @"申请退款中";
            _contentLabel.text = @"您对本次服务不满意，请耐心导师的回应";
            
            _payButton.frame = CGRectZero;
            _QuerryCancelbutton.frame = CGRectZero;
            [self.inspectConsultbutton mas_makeConstraints:^(MASConstraintMaker *make) {
                make.bottom.equalTo(self.view.mas_bottom).offset(0);
                make.left.equalTo(self.view.mas_left).offset(0);
                make.right.equalTo(self.view.mas_right).offset(-(WIDTH));
                make.top.equalTo(self.view.mas_top).offset(HEIGHT);
            }];

        } else if ([[self.orderForDetailM.state substringToIndex:4] isEqualToString:@"1300"]) {
        
            _contentTitleLabel.text = @"申请退款中";
            _contentLabel.text = @"您对本次服务不满意，导师不同意退款，客服已介入";
            
            _payButton.frame = CGRectZero;
            _QuerryCancelbutton.frame = CGRectZero;
            [self.inspectConsultbutton mas_makeConstraints:^(MASConstraintMaker *make) {
                make.bottom.equalTo(self.view.mas_bottom).offset(0);
                make.left.equalTo(self.view.mas_left).offset(0);
                make.right.equalTo(self.view.mas_right).offset(-(WIDTH));
                make.top.equalTo(self.view.mas_top).offset(HEIGHT);
            }];

        } else if ([[self.orderForDetailM.state substringToIndex:4] isEqualToString:@"0900"]) {
        
            _contentTitleLabel.text = @"服务完成";
            _contentLabel.text = @"已确定订单，导师收款后对本次服务进行评价";
            
            _payButton.frame = CGRectZero;
            _QuerryCancelbutton.frame = CGRectZero;
            [self.inspectConsultbutton mas_makeConstraints:^(MASConstraintMaker *make) {
                make.bottom.equalTo(self.view.mas_bottom).offset(0);
                make.left.equalTo(self.view.mas_left).offset(0);
                make.right.equalTo(self.view.mas_right).offset(-(WIDTH));
                make.top.equalTo(self.view.mas_top).offset(HEIGHT);
            }];
        } else if ([[self.orderForDetailM.state substringToIndex:4] isEqualToString:@"0700"]) {
        
            _contentTitleLabel.text = @"申请退款中";
            _contentLabel.text = @"您对本次服务不满意，导师同意退款，我们会及时将咨询费用退回到您的支付宝";
            
            _payButton.frame = CGRectZero;
            _QuerryCancelbutton.frame = CGRectZero;
            [self.inspectConsultbutton mas_makeConstraints:^(MASConstraintMaker *make) {
                make.bottom.equalTo(self.view.mas_bottom).offset(0);
                make.left.equalTo(self.view.mas_left).offset(0);
                make.right.equalTo(self.view.mas_right).offset(-(WIDTH));
                make.top.equalTo(self.view.mas_top).offset(HEIGHT);
            }];
        }
        
        self.oneLabel.backgroundColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        _oneLabelLine.backgroundColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        _applyLabel.textColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        self.twolabel.backgroundColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        _twoLabelLine.backgroundColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        _confirmLabel.textColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        self.threeLabel.backgroundColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        _threeLabelLine.backgroundColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        _consultLabel.textColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        self.fourlabel.backgroundColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        _fourLabelLine.backgroundColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        _severLabel.textColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        
    } else if (self.tagforColor == 5) { //评论
    
        if ([[self.orderForDetailM.state substringToIndex:4] isEqualToString:@"1000"]) {
            
            _contentTitleLabel.text = @"咨询结束";
            _contentLabel.text = @"请给出您的宝贵评价";
            
            _payButton.frame =  CGRectMake(WIDTH / 2 - 80, _contentLabel.frame.origin.y + _contentLabel.frame.size.height + 140, 160, 40);
            [_payButton setTitle:@"评论" forState:UIControlStateNormal];
            _QuerryCancelbutton.frame = CGRectZero;
        } else {
        
            _contentTitleLabel.text = @"咨询结束";
            _contentLabel.text = @"您已给出宝贵评价，期待导师的评价";
            
            _payButton.frame = CGRectZero;
            _QuerryCancelbutton.frame = CGRectZero;
        }
    } else if (self.tagforColor == 6) { //结束
        
        _contentTitleLabel.text = @"咨询结束";
        _contentLabel.text = @"咨询已结束，如有疑问请拨打：0571—886415101。";
        [_inspectConsultbutton setTitle:@"查看咨询内容" forState:UIControlStateNormal];
        
        //_QuerryCancelbutton.frame = CGRectMake(120, _contentLabel.frame.origin.y + _contentLabel.frame.size.height + 160, self.view.frame.size.width - 240, 40);
        //[_QuerryCancelbutton setTitle:@"评价导师" forState:UIControlStateNormal];
        _QuerryCancelbutton.frame = CGRectZero;
        _payButton.frame = CGRectZero;
        [self.inspectConsultbutton mas_makeConstraints:^(MASConstraintMaker *make) {
            make.bottom.equalTo(self.view.mas_bottom).offset(0);
            make.left.equalTo(self.view.mas_left).offset(0);
            make.right.equalTo(self.view.mas_right).offset(-(WIDTH));
            make.top.equalTo(self.view.mas_top).offset(HEIGHT);
        }];

        self.oneLabel.backgroundColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        _oneLabelLine.backgroundColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        _applyLabel.textColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        self.twolabel.backgroundColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        _twoLabelLine.backgroundColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        _confirmLabel.textColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        self.threeLabel.backgroundColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        _threeLabelLine.backgroundColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        _consultLabel.textColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        self.fourlabel.backgroundColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        _fourLabelLine.backgroundColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        _severLabel.textColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        _fiveLabel.backgroundColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
        _judgeLabel.textColor = [UIColor colorWithRed:71/255.0 green:173/255.0 blue:227/255.0 alpha:1.0];
    }
    
    self.cancelConsultV = [[cancelConsultView alloc] initWithFrame:CGRectMake(0, self.view.frame.size.height, self.view.frame.size.width, self.view.frame.size.height)];
    self.cancelConsultV.backgroundColor = [[UIColor blackColor] colorWithAlphaComponent:0.8f];
    self.cancelConsultV.cancelOrderM = _orderForDetailM;
    [self.view addSubview:self.cancelConsultV];
    
    self.cancelConsultV.myDelegate = self;
    
    //订单取消提示页面
    self.detailCancelConsultV = [[detailCancelConsultView alloc] initWithFrame:CGRectMake(0, HEIGHT, WIDTH, HEIGHT)];
    self.detailCancelConsultV.backgroundColor = [[UIColor blackColor] colorWithAlphaComponent:0.8f];
    [self.view addSubview:self.detailCancelConsultV];
    
    //评论
    self.judgeForServiceV = [[judgementView alloc] initWithFrame:CGRectMake(0, HEIGHT, WIDTH, HEIGHT)];
    self.judgeForServiceV.backgroundColor = [[UIColor blackColor] colorWithAlphaComponent:0.8f];
    self.judgeForServiceV.myDelegate = self;
    [self.view addSubview:self.judgeForServiceV];
}

#pragma mark -- buttonAction
//动画弹窗 取消订单之咨询页面
-(void)QuerryCancelbuttonAction:(UIButton *)button
{
    if ([[self.orderForDetailM.state substringToIndex:4] isEqualToString:@"0400"] || [[self.orderForDetailM.state substringToIndex:4] isEqualToString:@"0500"] || [[self.orderForDetailM.state substringToIndex:4] isEqualToString:@"0300"] || [[self.orderForDetailM.state substringToIndex:4] isEqualToString:@"0100"]) {
        
        [UIView animateWithDuration:0.5 delay:0.5 usingSpringWithDamping:10 initialSpringVelocity:0 options:UIViewAnimationOptionCurveEaseInOut animations:^{
            
            self.cancelConsultV.frame = CGRectMake(self.view.frame.origin.x, self.view.frame.origin.y, self.view.frame.size.width, self.view.frame.size.height);
            
            
        } completion:^(BOOL finished) {
            
        }];
    } else {
    
        [self showAlert:@"订单正在处理中，请耐心等待"];
    }
}

//返回
-(void)detailQuerryInfoBackbutton:(UIButton *)button{
    
    [self.navigationController popToRootViewControllerAnimated:YES];
    
}

//查看详情
-(void)inspectConsultButtonAction:(UIButton *)button{
    
    inspectConsultDetailViewController *inspectConsultDetailVC = [[inspectConsultDetailViewController alloc]init];
    inspectConsultDetailVC.orderForInspect = self.orderForDetailM;
    [self.navigationController pushViewController:inspectConsultDetailVC animated:YES];
}

//付款 和 评论
-(void)payOrCommentsAction:(UIButton *)button
{
    if ([button.titleLabel.text isEqualToString:@"支付"]) {
        
        [self simplePayAction];
    } else if ([button.titleLabel.text isEqualToString:@"满意"]) {
    
        [self satisfyToService];
    } else if ([button.titleLabel.text isEqualToString:@"评论"]) {
    
        [UIView animateWithDuration:0.5f animations:^{
            
            self.judgeForServiceV.orderForJudgeM = self.orderForDetailM;
            self.judgeForServiceV.frame = CGRectMake(0, 0, WIDTH, HEIGHT);
        }];
    }
}

-(void)alertToLogin:(NSInteger)btIndex
{
    if (btIndex == 0) {
        
        [self.navigationController popToRootViewControllerAnimated:YES];
    } else {
        
        LoginViewController *loginVC = [[LoginViewController alloc] init];
        loginVC.modalTransitionStyle = UIModalTransitionStyleCrossDissolve;
        
        [self presentViewController:loginVC animated:YES completion:^{
            
        }];
    }
}

#pragma mark -- cancelConsultViewDelegate
-(void)popFromCancelConsult
{
    if (self.tagforColor == 0) {
        
        [self cancelOrderForStu];
    } else if (self.tagforColor == 1) {
    
        [self cancelOrderForPayed];
    } else if (self.tagforColor == 2) {
    
        [self cancelOrderForEnsured];
    } else if (self.tagforColor == 3) {
    
        [self cancelOrderForEnsureTime];
    } else if (self.tagforColor == 4) {
    
        [self dissatisfyToService];
    }
}

#pragma mark -- 取消订单之还未支付
-(void)cancelOrderForStu
{
    [AFNConnect AFNConnectWithUrl:HTTPREQUEST withBodyData:[AFNConnect createDataForClientOrderAction:@"cancelOrder" withUserID:[[[NSUserDefaults standardUserDefaults] objectForKey:@"userInfo"] objectForKey:@"uid"] withOrderID:self.orderForDetailM.orderId] connectBlock:^(id data) {
        
        NSDictionary *dic = [NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingMutableContainers error:nil];
        
        if ([[dic objectForKey:@"state"] isEqualToString:@"error"]) {
            
            if ([[dic objectForKey:@"msg"] isEqualToString:@"uid is not existed"]) {
                
                [[NSUserDefaults standardUserDefaults] setObject:@"0" forKey:@"isLogin"];
                
                UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"提示" message:@"登录超时，请重新登录" delegate:self cancelButtonTitle:@"取消" otherButtonTitles:@"重新登录", nil];
                [alert show];
            } else {
                
                [self showAlert:@"订单正在处理中"];
            }
        } else if ([[dic objectForKey:@"state"] isEqualToString:@"success"]){
        
            [self detailCancelConsult];
        }
    }];
}

//取消咨询成功提示
- (void)detailCancelConsult{//时间
    
    [UIView beginAnimations:@"view动画" context:nil];
    //动画时长
    [UIView setAnimationDuration:0.5];
    self.detailCancelConsultV.frame = CGRectMake(0, 0, WIDTH, HEIGHT);
    //开始动画
    [UIView commitAnimations];  //出来
    
    [NSTimer scheduledTimerWithTimeInterval:1.0f
                                     target:self
                                   selector:@selector(detailCancelConsultVPush)
                                   userInfo:nil
                                    repeats:YES];
}

//回收弹窗
-(void)detailCancelConsultVPush{
    [UIView animateWithDuration:0.4 delay:0.1 options:UIViewAnimationOptionAllowAnimatedContent animations:^{
        
        self.detailCancelConsultV.frame = CGRectMake(0, HEIGHT, WIDTH, HEIGHT);
    } completion:^(BOOL finished) {
        
        [self.navigationController popToRootViewControllerAnimated:YES];
    }];
}

#pragma mark -- 取消订单 之 完成支付，导师未确认状态
-(void)cancelOrderForPayed
{
    [AFNConnect AFNConnectWithUrl:HTTPREQUEST withBodyData:[AFNConnect createDataForClientOrderAction:@"cancelOrderAfterPay" withUserID:[[[NSUserDefaults standardUserDefaults] objectForKey:@"userInfo"] objectForKey:@"uid"] withOrderID:self.orderForDetailM.orderId] connectBlock:^(id data) {
        
        NSDictionary *dic = [NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingMutableContainers error:nil];
        
        if ([[dic objectForKey:@"state"] isEqualToString:@"error"]) {
            
            if ([[dic objectForKey:@"msg"] isEqualToString:@"uid is not existed"]) {
                
                [[NSUserDefaults standardUserDefaults] setObject:@"0" forKey:@"isLogin"];
                
                UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"提示" message:@"登录超时，请重新登录" delegate:self cancelButtonTitle:@"取消" otherButtonTitles:@"重新登录", nil];
                [alert show];
                
            } else {
            
                [self showAlert:@"订单正在处理中，请耐心等待"];
            }
        } else if ([[dic objectForKey:@"state"] isEqualToString:@"success"]) {
        
            [self detailCancelConsult];  //取消服务已成功
        }
    }];
}

#pragma mark -- 取消订单 之 导师已确定
-(void)cancelOrderForEnsured
{
    [AFNConnect AFNConnectWithUrl:HTTPREQUEST withBodyData:[AFNConnect createDataForClientOrderAction:@"cancelOrderAfterAccept" withUserID:[[[NSUserDefaults standardUserDefaults] objectForKey:@"userInfo"] objectForKey:@"uid"] withOrderID:self.orderForDetailM.orderId] connectBlock:^(id data) {
        
        NSDictionary *dic = [NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingMutableContainers error:nil];
        
        if ([[dic objectForKey:@"state"] isEqualToString:@"success"]) {
            
            [self detailCancelConsult];
        } else if ([[dic objectForKey:@"state"] isEqualToString:@"error"]) {
        
            if ([[dic objectForKey:@"msg"] isEqualToString:@"uid is not existed"]) {
                
                [[NSUserDefaults standardUserDefaults] setObject:@"0" forKey:@"isLogin"];
                UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"提示" message:@"登录超时，请重新登录" delegate:self cancelButtonTitle:@"取消" otherButtonTitles:@"重新登录", nil];
                [alert show];
            } else {
            
                [self showAlert:@"订单正在处理中，请耐心等待"];
            }
        }
    }];
}

#pragma mark -- 取消订单 之 已经约定好时间
-(void)cancelOrderForEnsureTime
{
    [AFNConnect AFNConnectWithUrl:HTTPREQUEST withBodyData:[AFNConnect createDataForClientOrderAction:@"cancelOrderAfterEnsure" withUserID:[[[NSUserDefaults standardUserDefaults] objectForKey:@"userInfo"] objectForKey:@"uid"] withOrderID:self.orderForDetailM.orderId] connectBlock:^(id data) {
        
        NSDictionary *dic = [NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingMutableContainers error:nil];
        
        if ([[dic objectForKey:@"state"] isEqualToString:@"success"]) {
            
            [self detailCancelConsult];
        } else if ([[dic objectForKey:@"state"] isEqualToString:@"error"]) {
        
            if ([[dic objectForKey:@"msg"] isEqualToString:@"uid is not existed"]) {
                
                [[NSUserDefaults standardUserDefaults] setObject:@"0" forKey:@"isLogin"];
                UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"提示" message:@"登录超时，请重新登录" delegate:self cancelButtonTitle:@"取消" otherButtonTitles:@"重新登录", nil];
                [alert show];
            } else {
            
                [self showAlert:@"订单正在处理中，请耐心等待"];
            }
        }
    }];
}

#pragma mark -- 用户满意 && 用户不满意
//满意
-(void)satisfyToService
{
    [AFNConnect AFNConnectWithUrl:HTTPREQUEST withBodyData:[AFNConnect createDataForClientOrderAction:@"satisfyOrder" withUserID:[[[NSUserDefaults standardUserDefaults] objectForKey:@"userInfo"] objectForKey:@"uid"] withOrderID:self.orderForDetailM.orderId] connectBlock:^(id data) {
        
        NSDictionary *dic = [NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingMutableContainers error:nil];
        
        if ([[dic objectForKey:@"state"] isEqualToString:@"error"]) {
            
            if ([[dic objectForKey:@"msg"] isEqualToString:@"order state is not accurate"]) {
                
                [self showAlert:@"您已确认完毕，等待导师回应"];
            } else if ([[dic objectForKey:@"msg"] isEqualToString:@"uid is not existed"]) {
                
                [[NSUserDefaults standardUserDefaults] setObject:@"0" forKey:@"isLogin"];
                
                UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"提示" message:@"登录超时，请重新登录" delegate:self cancelButtonTitle:@"取消" otherButtonTitles:@"重新登录", nil];
                [alert show];
            }
        } else if ([[dic objectForKey:@"state"] isEqualToString:@"success"]) {
        
            UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"提示" message:[NSString stringWithFormat:@"订单号%@已确认咨询，请在导师收款后进行评价", self.orderForDetailM.orderId] delegate:self cancelButtonTitle:@"确认" otherButtonTitles:nil];
            [self.view addSubview:alert];
            
            [alert show];
        }
    }];
}

//不满意
-(void)dissatisfyToService
{
    [AFNConnect AFNConnectWithUrl:HTTPREQUEST withBodyData:[AFNConnect createDataForClientOrderAction:@"dissatisfyOrder" withUserID:[[[NSUserDefaults standardUserDefaults] objectForKey:@"userInfo"] objectForKey:@"uid"] withOrderID:self.orderForDetailM.orderId] connectBlock:^(id data) {
        
        NSDictionary *dic = [NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingMutableContainers error:nil];
        
        if ([[dic objectForKey:@"state"] isEqualToString:@"error"]) {
            
            if ([[dic objectForKey:@"msg"] isEqualToString:@"order state is not accurate"]) {
                
                [self showAlert:@"您已确认完毕，等待导师回应"];
            } else if ([[dic objectForKey:@"msg"] isEqualToString:@"uid is not existed"]) {
                
                [[NSUserDefaults standardUserDefaults] setObject:@"0" forKey:@"isLogin"];
                UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"提示" message:@"登录超时，请重新登录" delegate:self cancelButtonTitle:@"取消" otherButtonTitles:@"重新登录", nil];
                [alert show];
            }
        } else if ([[dic objectForKey:@"state"] isEqualToString:@"success"]) {
        
            [self detailCancelConsult];
        }
    }];
}

#pragma mark -- alertviewDelegate
-(void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex
{
    if ([alertView.message isEqualToString:@"登录超时，请重新登录"]) {
        
        if (buttonIndex == 0) {
            
            [self.navigationController popToRootViewControllerAnimated:YES];
        } else if (buttonIndex == 1) {
        
            LoginViewController *loginVC = [[LoginViewController alloc] init];
            loginVC.modalTransitionStyle = UIModalTransitionStyleCrossDissolve;
            
            [self presentViewController:loginVC animated:YES completion:^{
                
            }];
        }
    }
}

#pragma mark -
#pragma mark   ==============点击订单模拟支付行为==============
//
//选中商品调用支付宝极简支付
//
-(void)simplePayAction
{
    self.product.subject = [self.orderForDetailM.title stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding];
    self.product.body = [NSString stringWithFormat:@"咨询"];
    self.product.price = [self.orderForDetailM.price floatValue];
    /*
     *商户的唯一的parnter和seller。
     *签约后，支付宝会为每个商户分配一个唯一的 parnter 和 seller。
     */
    
    /*============================================================================*/
    /*=======================需要填写商户app申请的===================================*/
    /*============================================================================*/
    NSString *partner = @"2088021531995773";  //商户ID
    NSString *seller = @"onemile666@yeah.net";    //支付宝账号
    NSString *privateKey = RSADataSignerKey;  // 数据安全，数据加密
    //支付宝提供
    /*============================================================================*/
    /*============================================================================*/
    /*============================================================================*/
    
    //partner和seller获取失败,提示
    if ([partner length] == 0 ||
        [seller length] == 0 ||
        [privateKey length] == 0)
    {
        UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"提示"
                                                        message:@"缺少partner或者seller或者私钥。"
                                                       delegate:self
                                              cancelButtonTitle:@"确定"
                                              otherButtonTitles:nil];
        [alert show];
        return;
    }
    
    // *********** 把商品转换成订单信息 *********
    /*
     *生成订单信息及签名
     */
    //将商品信息赋予AlixPayOrder的成员变量
    Order *order = [[Order alloc] init];
    order.partner = partner;   //设置商户ID
    order.seller = seller;     //设置支付宝账号
    //订单ID在系统中必须是唯一的
    //以时间 + 随机数 （订单生成规则）
    order.tradeNO = self.orderForDetailM.orderId; //订单ID（由商家自行制定）   订单编号（即我们来写）
    order.productName = _product.subject; //商品标题
    order.productDescription = _product.body; //商品描述
    order.amount = @"0.01"; //[NSString stringWithFormat:@"%.2f",_product.price]; //商品价格
    order.notifyURL =  @"http://service.1yingli.cn/yiyingliService/TradeNotifyProcess"; //回调URL
    
    order.service = @"mobile.securitypay.pay";
    order.paymentType = @"1";  //支付类型  1代表支付 2代表全额退款  3代表取消支付 4代表部分退款   仅支持1
    order.inputCharset = @"utf-8";
    order.itBPay = @"30m";
    order.showUrl = @"m.alipay.com";
    
    //应用注册scheme,在AlixPayDemo-Info.plist定义URL types
    NSString *appScheme = @"onemile";  //跳转
    
    //将订单拼接成字符串
    NSString *orderSpec = [order description];
    //NSLog(@"orderSpec = %@",orderSpec);
    
    //获取私钥并将商户信息签名,外部商户可以根据情况存放私钥和签名,只需要遵循RSA签名规范,并将签名字符串base64编码和UrlEncode
    id<DataSigner> signer = CreateRSADataSigner(privateKey);   //signer签名对象
    
    //签名数据
    /*
     * 签名 类似于每个人的指纹
     * abc 签名 ——> 得到一个指纹（字符串）
     * abd 签名 ——> 得到一个指纹（字符串）
     
     * 支付
     * total_fee = 0.02&seller_id=xxx@qq.com&sign=ABC ——> 签名 ——> 指纹（ABC）
     * total_fee = 0.03&seller_id=xxx@qq.com ——> 签名 ——> 指纹（ABD）  数据不同，得到的签名指纹不同
     */
    NSString *signedString = [signer signString:orderSpec];
    
    //将签名成功字符串格式化为订单字符串,请严格按照该格式
    NSString *orderString = nil;
    if (signedString != nil) {
        orderString = [NSString stringWithFormat:@"%@&sign=\"%@\"&sign_type=\"%@\"",
                       orderSpec, signedString, @"RSA"];
        
        //没有钱包，唤起支付宝页面
        ((UIWindow *)[[[UIApplication sharedApplication] windows] objectAtIndex:0]).hidden = NO;
        
        [[AlipaySDK defaultService] payOrder:orderString fromScheme:appScheme callback:^(NSDictionary *resultDic) {
            
            ((UIWindow *)[[[UIApplication sharedApplication] windows] objectAtIndex:0]).hidden = YES;
            //NSLog(@"reslut = %@",resultDic);
            
            if ([[resultDic objectForKey:@"resultStatus"] isEqualToString:@"9000"]) {
                
                [self.navigationController popToRootViewControllerAnimated:YES];
            } else if ([[resultDic objectForKey:@"resultStatus"] isEqualToString:@"8000"]) {
            
                [self showAlert:@"订单正在处理中。。。"];
            } else if ([[resultDic objectForKey:@"resultStatus"] isEqualToString:@"4000"]) {
            
                [self showAlert:@"订单支付失败"];
            }
        }];
        //[tableView deselectRowAtIndexPath:indexPath animated:YES];
    }
    
}

//警示框回弹自动
- (void) showAlert:(NSString *)message{//时间
    
    UIAlertView *promptAlert = [[UIAlertView alloc] initWithTitle:@"提示" message:message delegate:nil cancelButtonTitle:nil otherButtonTitles:nil];
    
    [NSTimer scheduledTimerWithTimeInterval:1.0f
                                     target:self
                                   selector:@selector(timerFireMethod:)
                                   userInfo:promptAlert
                                    repeats:YES];
    [promptAlert show];
}

- (void)timerFireMethod:(NSTimer *)theTimer//弹出框
{
    UIAlertView *promptAlert = (UIAlertView * )[theTimer userInfo];
    [promptAlert dismissWithClickedButtonIndex:0 animated:NO];
    promptAlert = NULL;
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
