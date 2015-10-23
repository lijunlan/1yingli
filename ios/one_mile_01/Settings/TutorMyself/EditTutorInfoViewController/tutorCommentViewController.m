//
//  tutorCommentViewController.m
//  one_mile_01
//
//  Created by 王进帅 on 15/9/22.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import "tutorCommentViewController.h"

@interface tutorCommentViewController ()

@property (assign, nonatomic) NSInteger nextPage;//上拉加载

@end

@implementation tutorCommentViewController

-(instancetype)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    if (self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil]) {
        
        self.commentArray = [[NSMutableArray alloc]init];
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
    self.tableView1 = [[UITableView alloc]initWithFrame:CGRectMake(self.view.frame.origin.x, self.view.frame.origin.y + 90, self.view.frame.size.width, self.view.frame.size.height - 50) style:UITableViewStylePlain];
    
    self.tableView1.showsVerticalScrollIndicator = NO;
    self.tableView1.backgroundColor = [UIColor clearColor];
    self.tableView1.delegate = self;
    self.tableView1.dataSource = self;
    self.tableView1.separatorStyle = UITableViewCellSeparatorStyleNone;
    [self.view addSubview:self.tableView1];
    
    [self.tableView1 addHeaderWithTarget:self action:@selector(headerRereshing)];
    [self.tableView1 headerBeginRefreshing];
    
    [self.tableView1 addFooterWithTarget:self action:@selector(addFooter)];
    
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
    
    // Do any additional setup after loading the view.
}

#pragma mark -- 获取数据
-(void)getDetailData
{
    [AFNConnect AFNConnectWithUrl:HTTPREQUEST withBodyData:[AFNConnect createDataForTeacherCommentList:self.DetialID withPage:[NSString stringWithFormat:@"%ld", (long)self.nextPage]] connectBlock:^(id data) {
        
        NSString *jsonStr = [[NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingMutableContainers error:nil] objectForKey:@"data"];
        
        NSArray *jsonArray = (NSArray *)[jsonStr objectFromJSONString];
        
        if (jsonArray.count != 0) {
            
            for (NSMutableDictionary *dic in jsonArray) {
                
                tutorCommentModel *tutorCommentM = [[tutorCommentModel alloc] init];
                
                tutorCommentM.commentId = [dic objectForKey:@"commentId"];
                tutorCommentM.content = [dic objectForKey:@"content"];
                tutorCommentM.score = [dic objectForKey:@"score"];
                tutorCommentM.createTime = [dic objectForKey:@"createTime"];
                tutorCommentM.nickName = [dic objectForKey:@"nickName"];
                tutorCommentM.iconUrl = [dic objectForKey:@"iconUrl"];
                tutorCommentM.serviceTitle = [dic objectForKey:@"serviceTitle"];
                [self.commentArray addObject:tutorCommentM];
            }
        }
        [self.tableView1 reloadData];
    }];
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    if (self.commentArray.count == 0) {
        return 0;
    }
    return self.commentArray.count;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    tutorCommentCell *cell = [self tableView:_tableView1 cellForRowAtIndexPath:indexPath];
    
    return cell.frame.size.height;
}
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *cellIdentifier = @"myCell";
    tutorCommentCell *cell = [tableView dequeueReusableCellWithIdentifier:cellIdentifier];
    if (cell == nil) {
        cell =[[tutorCommentCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:cellIdentifier];
        cell.selectionStyle = UITableViewCellSelectionStyleNone;//取消cell点击效果
        cell.backgroundColor = [UIColor clearColor];
    }
    
    tutorCommentModel *tmp = [self.commentArray objectAtIndex:indexPath.row];
    [cell setlabel2Text:tmp.content];
    cell.label2.text = tmp.serviceTitle;
    [cell.imageView1 sd_setImageWithURL:[NSURL URLWithString:tmp.iconUrl]];
    cell.label3.text = tmp.nickName;
    cell.label4.text = tmp.createTime;
    
    return cell;
}

#pragma mark -- 下拉刷新 上拉加载更多
//下拉刷新
-(void)headerRereshing{
    
    self.isUpLoading = NO;//标记为下拉操作
    self.nextPage = 1;
    [self.commentArray removeAllObjects];
    [self getDetailData];

    dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(2.0 * NSEC_PER_SEC)), dispatch_get_main_queue(), ^{
        
        [self.tableView1 reloadData];
        [self.tableView1 headerEndRefreshing];
    });
}
//上拉加载更多
- (void)addFooter
{
    self.nextPage ++;
    self.isUpLoading = YES;//标记为上拉操作
    
    [self getDetailData];//请求数据
    
    dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(2.0 * NSEC_PER_SEC)), dispatch_get_main_queue(), ^{
        [self.tableView1 reloadData];
        [self.tableView1 footerEndRefreshing];
    });
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
