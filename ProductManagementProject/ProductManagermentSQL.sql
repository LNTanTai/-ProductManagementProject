USE [ProductManagement]
GO
/****** Object:  Table [dbo].[TblCategories]    Script Date: 4/15/2021 10:18:42 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[TblCategories](
	[categoryID] [varchar](10) NOT NULL,
	[categoryName] [nvarchar](50) NULL,
	[description] [nvarchar](200) NULL,
 CONSTRAINT [PK_tblCategories] PRIMARY KEY CLUSTERED 
(
	[categoryID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[TblProducts]    Script Date: 4/15/2021 10:18:42 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[TblProducts](
	[productID] [varchar](10) NOT NULL,
	[productName] [nvarchar](50) NULL,
	[unit] [varchar](50) NULL,
	[price] [float] NULL,
	[quantity] [int] NULL,
	[categoryid] [varchar](10) NULL,
 CONSTRAINT [PK_TblProducts] PRIMARY KEY CLUSTERED 
(
	[productID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[TblUsers]    Script Date: 4/15/2021 10:18:42 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[TblUsers](
	[userID] [varchar](10) NOT NULL,
	[fullName] [nvarchar](50) NULL,
	[password] [varchar](50) NULL,
	[status] [bit] NULL,
 CONSTRAINT [PK_TblUsers] PRIMARY KEY CLUSTERED 
(
	[userID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
INSERT [dbo].[TblCategories] ([categoryID], [categoryName], [description]) VALUES (N'CA001', N'Thanh Phong', N'ohyeah')
INSERT [dbo].[TblCategories] ([categoryID], [categoryName], [description]) VALUES (N'CA002', N'Nguyen Hien', N'Yeah')
INSERT [dbo].[TblUsers] ([userID], [fullName], [password], [status]) VALUES (N'AnhKhoa', N'Truong Anh Khoa', N'789', 0)
INSERT [dbo].[TblUsers] ([userID], [fullName], [password], [status]) VALUES (N'AnhTuan', N'Nguyen Anh Tuan', N'123', 1)
INSERT [dbo].[TblUsers] ([userID], [fullName], [password], [status]) VALUES (N'PhuongNam', N'Tran Phuong Nam', N'456', 1)
INSERT [dbo].[TblUsers] ([userID], [fullName], [password], [status]) VALUES (N'QuangLoc', N'Le Quang Loc', N'110', 1)
INSERT [dbo].[TblUsers] ([userID], [fullName], [password], [status]) VALUES (N'QuocBao', N'Nguyen Le Quoc Bao', N'122', 1)
INSERT [dbo].[TblUsers] ([userID], [fullName], [password], [status]) VALUES (N'TanTai', N'Lai Nguyen Tan Tai', N'111', 0)
ALTER TABLE [dbo].[TblProducts]  WITH CHECK ADD  CONSTRAINT [FK_TblProducts_TblCategories] FOREIGN KEY([categoryid])
REFERENCES [dbo].[TblCategories] ([categoryID])
GO
ALTER TABLE [dbo].[TblProducts] CHECK CONSTRAINT [FK_TblProducts_TblCategories]
GO
