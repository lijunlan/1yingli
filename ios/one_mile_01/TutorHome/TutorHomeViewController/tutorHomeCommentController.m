//
//  tutorHomeCommentController.m
//  one_mile_01
//
//  Created by 王进帅 on 15/9/22.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import "tutorHomeCommentController.h"

@interface tutorHomeCommentController ()

@end

@implementation tutorHomeCommentController

-(instancetype)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    if (self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil]) {
        
        self.tutorHomeCommentArray = [[NSMutableArray alloc]init];
        self.nextPage = 1;
    }
    return self;
}


- (void)viewDidLoad {
    [super viewDidLoad];
    
    self.view.backgroundColor = [UIColor colorWithRed:237/255.0 green:239/255.0 blue:240/255.0 alpha:1.0];
    
    //    返回按钮
    UIButton *button = [UIButton buttonWithType:UIButtonTypeCustom];
    button.frame = CGRectMake(20, 40, PUSHANDPOPBUTTONSIZE, PUSHANDPOPBUTTONSIZE);
    [button setBackgroundImage:[UIImage imageNamed:@"pop_dark.png"] forState:UIControlStateNormal];
    button.backgroundColor = [UIColor clearColor];
    [button addTarget:self action:@selector(backbutton:) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:button];
    
    
    self.navigationController.navigationBar.hidden = YES;
    UILabel *titleLabel = [[UILabel alloc]initWithFrame:CGRectMake(20 + PUSHANDPOPBUTTONSIZE, button.frame.origin.y,WIDTH - 70 - 20 - PUSHANDPOPBUTTONSIZE, 30)];
    titleLabel.text = @"查看导师评价";
    titleLabel.textAlignment = NSTextAlignmentCenter;
    titleLabel.font = [UIFont systemFontOfSize:19];
    titleLabel.textColor = [UIColor lightGrayColor];
    [self.view addSubview:titleLabel];
    
    //    学员评价tableView
    self.tutorHomeCommentTV = [[UITableView alloc]initWithFrame:CGRectMake(self.view.frame.origin.x, self.view.frame.origin.y + 90, self.view.frame.size.width, self.view.frame.size.height - 100) style:UITableViewStylePlain];
    
    self.tutorHomeCommentTV.showsVerticalScrollIndicator = NO;
    self.tutorHomeCommentTV.backgroundColor = [UIColor clearColor];
    self.tutorHomeCommentTV.delegate = self;
    self.tutorHomeCommentTV.dataSource = self;
    self.tutorHomeCommentTV.separatorStyle = UITableViewCellSeparatorStyleNone;
    [self.view addSubview:self.tutorHomeCommentTV];
    
    self.imageView1 = [[UIImageView alloc]init];
    [self.view addSubview:self.imageView1];
    [self.imageView1 mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(self.view.mas_top).offset(25);
        make.right.equalTo(self.view.mas_right).offset(-20);
        make.width.offset(50);
        make.height.offset(50);
        
    }];
    self.imageView1.backgroundColor = [UIColor yellowColor];
    self.imageView1.layer.masksToBounds = YES;
    self.imageView1.layer.cornerRadius = 24;
    [self.imageView1 sd_setImageWithURL:[NSURL URLWithString:[self.photoURLForComments stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding]] placeholderImage:[UIImage imageNamed:@"placeholders.png"]];
    
    [self.tutorHomeCommentTV addHeaderWithTarget:self action:@selector(headerRereshing)];
    [self.tutorHomeCommentTV headerBeginRefreshing];
    
    [self.tutorHomeCommentTV addFooterWithTarget:self action:@selector(addFooter)];
    
    // Do any additional setup after loading the view.
}

-(void)getDetailData
{
    [AFNConnect AFNConnectWithUrl:HTTPREQUEST withBodyData:[AFNConnect createDataForTeacherCommentList:self.DetialID withPage:[NSString stringWithFormat:@"%ld", (long)self.nextPage]] connectBlock:^(id data) {
        
        NSString *jsonStr = [[NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingMutableContainers error:nil] objectForKey:@"data"];
        
        NSArray *jsonArray = (NSArray *)[jsonStr objectFromJSONString];
        
        if (jsonArray.count != 0) {
            
            for (NSMutableDictionary *dic in jsonArray) {
                
                tutorHomeCommentModal *tutorHomeCommentM = [[tutorHomeCommentModal alloc] init];
                
                tutorHomeCommentM.commentId = [dic objectForKey:@"commentId"];
                tutorHomeCommentM.content = [dic objectForKey:@"content"];
                tutorHomeCommentM.score = [dic objectForKey:@"score"];
                tutorHomeCommentM.createTime = [dic objectForKey:@"createTime"];
                tutorHomeCommentM.nickName = [dic objectForKey:@"nickName"];
                tutorHomeCommentM.iconUrl = [dic objectForKey:@"iconUrl"];
                tutorHomeCommentM.serviceTitle = [dic objectForKey:@"serviceTitle"];
            
                [self.tutorHomeCommentArray addObject:tutorHomeCommentM];
            }
        }
        [self.tutorHomeCommentTV reloadData];
    }];
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    if (self.tutorHomeCommentArray.count == 0) {
        return 0;
    }
    return self.tutorHomeCommentArray.count;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    tutorHomeCommentCell *cell = [self tableView:_tutorHomeCommentTV cellForRowAtIndexPath:indexPath];
    
    return cell.frame.size.height;
}
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *cellIdentifier = @"myCell";
    tutorHomeCommentCell *cell = [tableView dequeueReusableCellWithIdentifier:cellIdentifier];
    if (cell == nil) {
        cell =[[tutorHomeCommentCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:cellIdentifier];
        cell.selectionStyle = UITableViewCellSelectionStyleNone;//取消cell点击效果
        cell.backgroundColor = [UIColor clearColor];
    }
    
    if (_tutorHomeCommentArray.count == 0) {
        return cell;
    }

    tutorHomeCommentModal *tmp = [self.tutorHomeCommentArray objectAtIndex:indexPath.row];
    cell.label2.text = tmp.serviceTitle;
        [cell setlabel2Text:tmp.content];
    [cell.imageView1 sd_setImageWithURL:[NSURL URLWithString:tmp.iconUrl] placeholderImage:[UIImage imageNamed:@"placeholders.png"]];
    cell.label3.text = tmp.nickName;
    cell.label4.text = tmp.createTime;
    
    return cell;
}

#pragma mark -- 下拉刷新 上拉加载
-(void)headerRereshing{
    
    self.nextPage = 1;
    [self.tutorHomeCommentArray removeAllObjects];
    [self getDetailData];

//    dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(2.0 * NSEC_PER_SEC)), dispatch_get_main_queue(), ^{
    
        [self.tutorHomeCommentTV reloadData];
        
        [self.tutorHomeCommentTV headerEndRefreshing];
//    });
}

#pragma mark —上拉加载更多
- (void)addFooter
{
    self.nextPage++;
    self.isUpLoading = YES;//标记为上拉操作
    
    [self getDetailData];//请求数据
    
//    dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(2.0 * NSEC_PER_SEC)), dispatch_get_main_queue(), ^{
    
        [self.tutorHomeCommentTV reloadData];
        [self.tutorHomeCommentTV footerEndRefreshing];
//    });
}

-(void)backbutton:(UIButton *)button{
    [self.navigationController popViewControllerAnimated:YES];
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
