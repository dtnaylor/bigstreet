USE [bigstreet]
GO
/****** Object:  User [IOWA\vpr-sql-bigstreet-owner]    Script Date: 11/21/2010 22:23:07 ******/
CREATE USER [IOWA\vpr-sql-bigstreet-owner] FOR LOGIN [IOWA\vpr-sql-bigstreet-owner]
GO
/****** Object:  User [bigstreet]    Script Date: 11/21/2010 22:23:07 ******/
CREATE USER [bigstreet] FOR LOGIN [bigstreet] WITH DEFAULT_SCHEMA=[dbo]
GO
/****** Object:  Table [dbo].[noc_codes_current]    Script Date: 11/21/2010 22:23:08 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[noc_codes_current](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[code] [char](4) NOT NULL,
	[name] [varchar](800) NOT NULL,
 CONSTRAINT [PK_noc_codes] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 90) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[nanda_codes_current]    Script Date: 11/21/2010 22:23:08 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[nanda_codes_current](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[code] [char](5) NOT NULL,
	[name] [varchar](800) NOT NULL,
 CONSTRAINT [PK_nanda_codes] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 90) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[nic_codes_current]    Script Date: 11/21/2010 22:23:08 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[nic_codes_current](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[code] [char](4) NOT NULL,
	[name] [varchar](800) NOT NULL,
 CONSTRAINT [PK_nic_codes] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 90) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[nic_codes_2005]    Script Date: 11/21/2010 22:23:08 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[nic_codes_2005](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[code] [varchar](50) NULL,
	[name] [varchar](255) NULL,
	[definition] [varchar](4000) NULL,
 CONSTRAINT [PK_linkagesbook_nic_codes] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 90) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[noc_codes_2005]    Script Date: 11/21/2010 22:23:08 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[noc_codes_2005](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[code] [varchar](50) NULL,
	[name] [varchar](255) NULL,
	[definition] [varchar](4000) NULL,
 CONSTRAINT [PK_linkagesbook_noc_codes] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 90) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[nanda_codes_retired]    Script Date: 11/21/2010 22:23:08 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[nanda_codes_retired](
	[nanda_code] [nvarchar](50) NULL,
	[name] [nvarchar](255) NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[nanda_codes_2005_to_current_mappings]    Script Date: 11/21/2010 22:23:08 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[nanda_codes_2005_to_current_mappings](
	[old_name] [nvarchar](255) NOT NULL,
	[new_name] [nvarchar](255) NOT NULL,
	[new_nanda_code] [nvarchar](10) NOT NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[two_item_correlation_diagnoses]    Script Date: 11/21/2010 22:23:08 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[two_item_correlation_diagnoses](
	[id] [varchar](10) NULL,
	[name] [varchar](255) NULL,
	[nanda_code] [char](5) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[two_item_correlation_interventions]    Script Date: 11/21/2010 22:23:08 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[two_item_correlation_interventions](
	[id] [varchar](10) NULL,
	[name_a] [varchar](255) NULL,
	[unknown_1] [varchar](50) NULL,
	[unknown_2] [varchar](50) NULL,
	[nic_code] [char](4) NULL,
	[unknown_3] [varchar](50) NULL,
	[unknown_4] [varchar](50) NULL,
	[name_nic] [varchar](255) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[two_item_correlation_outcomes]    Script Date: 11/21/2010 22:23:08 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[two_item_correlation_outcomes](
	[code] [varchar](50) NULL,
	[name_a] [varchar](255) NULL,
	[unknown_1] [varchar](50) NULL,
	[unknown_2] [varchar](50) NULL,
	[name_b] [varchar](255) NULL,
	[unknown_3] [varchar](50) NULL,
	[unknown_4] [varchar](50) NULL,
	[name_c] [varchar](255) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[diagnoses]    Script Date: 11/21/2010 22:23:08 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[diagnoses](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[nanda_code] [char](5) NULL,
	[name_2005] [nvarchar](255) NULL,
	[name_current] [nvarchar](255) NULL,
	[name_linkages_book] [nvarchar](255) NULL,
	[definition] [text] NULL,
	[source] [nvarchar](50) NULL,
	[status] [nvarchar](50) NULL,
 CONSTRAINT [PK_nanda_codes_1] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 90) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[outcomes]    Script Date: 11/21/2010 22:23:08 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[outcomes](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[noc_code] [nchar](4) NOT NULL,
	[name_current] [nvarchar](255) NULL,
	[name_2005] [nvarchar](255) NULL,
	[name_linkages_book] [nvarchar](255) NULL,
	[definition] [text] NULL,
 CONSTRAINT [PK_outcomes] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 90) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[interventions]    Script Date: 11/21/2010 22:23:08 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[interventions](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[nic_code] [char](4) NOT NULL,
	[name_2005] [varchar](255) NULL,
	[name_current] [varchar](255) NULL,
	[name_linkages_book] [varchar](255) NULL,
	[definition] [text] NULL,
 CONSTRAINT [PK_interventions] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 90) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[diagnosis_outcome_interventions]    Script Date: 11/21/2010 22:23:08 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[diagnosis_outcome_interventions](
	[diagnosis_id] [int] NOT NULL,
	[outcome_id] [int] NOT NULL,
	[intervention_id] [int] NOT NULL,
	[type] [varchar](50) NOT NULL,
 CONSTRAINT [PK_diagnosis_outcome_interventions] PRIMARY KEY CLUSTERED 
(
	[diagnosis_id] ASC,
	[outcome_id] ASC,
	[intervention_id] ASC,
	[type] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 90) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[linkagesbook_diagnoses]    Script Date: 11/21/2010 22:23:08 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[linkagesbook_diagnoses](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](255) NULL,
	[definition] [nvarchar](2000) NULL,
	[nanda_code] [nvarchar](50) NULL,
 CONSTRAINT [PK_linksagesbook_diagnoses] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 90) ON [PRIMARY]
) ON [PRIMARY]
GO
CREATE UNIQUE NONCLUSTERED INDEX [ix_linkagesbook_diagnoses_name] ON [dbo].[linkagesbook_diagnoses] 
(
	[name] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 90) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[two_item_correlations]    Script Date: 11/21/2010 22:23:08 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[two_item_correlations](
	[first_id] [varchar](50) NULL,
	[second_id] [varchar](50) NULL,
	[first_support] [float] NULL,
	[second_support] [float] NULL,
	[support] [float] NULL,
	[all_confidence] [float] NULL,
	[any_confidence] [float] NULL,
	[bond] [float] NULL,
	[simplified_chi_square] [float] NULL,
	[probability_ratio] [varchar](50) NULL,
	[leverage] [float] NULL,
	[likelihood_ratio] [float] NULL,
	[bcpnn] [float] NULL,
	[simplified_chi_square_continuity_correction] [float] NULL,
	[is] [float] NULL,
	[two_way_support] [varchar](50) NULL,
	[simplified_chi_square_continuity_support] [float] NULL,
	[phi_coefficient] [float] NULL,
	[relative_risk] [float] NULL,
	[odds_ratio] [float] NULL,
	[conviction] [float] NULL,
	[added_value] [float] NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[diagnosis_outcomes]    Script Date: 11/21/2010 22:23:08 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[diagnosis_outcomes](
	[diagnosis_id] [int] NOT NULL,
	[outcome_id] [int] NOT NULL,
 CONSTRAINT [PK_diagnosis_outcomes] PRIMARY KEY CLUSTERED 
(
	[diagnosis_id] ASC,
	[outcome_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 90) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[linkagesbook_outcomes]    Script Date: 11/21/2010 22:23:08 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[linkagesbook_outcomes](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[diagnosis_id] [int] NULL,
	[name] [nvarchar](255) NULL,
	[definition] [nvarchar](2000) NULL,
	[noc_code_exception] [nvarchar](10) NULL,
 CONSTRAINT [PK_linkagesbook_outcomes] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 90) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[linkagesbook_interventions]    Script Date: 11/21/2010 22:23:08 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[linkagesbook_interventions](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[outcome_id] [int] NOT NULL,
	[name] [nvarchar](255) NOT NULL,
	[type] [nvarchar](50) NOT NULL,
	[nic_code] [nvarchar](10) NULL,
 CONSTRAINT [PK_linkagesbook_interventions] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 90) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  View [dbo].[linkagesbook_interventions_corrected]    Script Date: 11/21/2010 22:23:09 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[linkagesbook_interventions_corrected]
AS
SELECT     dbo.linkagesbook_interventions.id, dbo.linkagesbook_interventions.outcome_id, dbo.linkagesbook_interventions.name, 
                      dbo.linkagesbook_interventions.type, dbo.nic_codes_2005.code AS nic_code, dbo.nic_codes_2005.definition
FROM         dbo.linkagesbook_interventions LEFT OUTER JOIN
                      dbo.nic_codes_2005 ON dbo.linkagesbook_interventions.nic_code = dbo.nic_codes_2005.code OR 
                      dbo.linkagesbook_interventions.name = dbo.nic_codes_2005.name
GO
EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPane1', @value=N'[0E232FF0-B466-11cf-A24F-00AA00A3EFFF, 1.00]
Begin DesignProperties = 
   Begin PaneConfigurations = 
      Begin PaneConfiguration = 0
         NumPanes = 4
         Configuration = "(H (1[40] 4[20] 2[20] 3) )"
      End
      Begin PaneConfiguration = 1
         NumPanes = 3
         Configuration = "(H (1 [50] 4 [25] 3))"
      End
      Begin PaneConfiguration = 2
         NumPanes = 3
         Configuration = "(H (1 [50] 2 [25] 3))"
      End
      Begin PaneConfiguration = 3
         NumPanes = 3
         Configuration = "(H (4 [30] 2 [40] 3))"
      End
      Begin PaneConfiguration = 4
         NumPanes = 2
         Configuration = "(H (1 [56] 3))"
      End
      Begin PaneConfiguration = 5
         NumPanes = 2
         Configuration = "(H (2 [66] 3))"
      End
      Begin PaneConfiguration = 6
         NumPanes = 2
         Configuration = "(H (4 [50] 3))"
      End
      Begin PaneConfiguration = 7
         NumPanes = 1
         Configuration = "(V (3))"
      End
      Begin PaneConfiguration = 8
         NumPanes = 3
         Configuration = "(H (1[56] 4[18] 2) )"
      End
      Begin PaneConfiguration = 9
         NumPanes = 2
         Configuration = "(H (1 [75] 4))"
      End
      Begin PaneConfiguration = 10
         NumPanes = 2
         Configuration = "(H (1[66] 2) )"
      End
      Begin PaneConfiguration = 11
         NumPanes = 2
         Configuration = "(H (4 [60] 2))"
      End
      Begin PaneConfiguration = 12
         NumPanes = 1
         Configuration = "(H (1) )"
      End
      Begin PaneConfiguration = 13
         NumPanes = 1
         Configuration = "(V (4))"
      End
      Begin PaneConfiguration = 14
         NumPanes = 1
         Configuration = "(V (2))"
      End
      ActivePaneConfig = 0
   End
   Begin DiagramPane = 
      Begin Origin = 
         Top = 0
         Left = 0
      End
      Begin Tables = 
         Begin Table = "linkagesbook_interventions"
            Begin Extent = 
               Top = 83
               Left = 123
               Bottom = 219
               Right = 274
            End
            DisplayFlags = 280
            TopColumn = 1
         End
         Begin Table = "nic_codes_2005"
            Begin Extent = 
               Top = 50
               Left = 425
               Bottom = 158
               Right = 576
            End
            DisplayFlags = 280
            TopColumn = 0
         End
      End
   End
   Begin SQLPane = 
   End
   Begin DataPane = 
      Begin ParameterDefaults = ""
      End
      Begin ColumnWidths = 9
         Width = 284
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
      End
   End
   Begin CriteriaPane = 
      Begin ColumnWidths = 11
         Column = 1440
         Alias = 900
         Table = 1170
         Output = 720
         Append = 1400
         NewValue = 1170
         SortType = 1350
         SortOrder = 1410
         GroupBy = 1350
         Filter = 1350
         Or = 1350
         Or = 1350
         Or = 1350
      End
   End
End
' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'linkagesbook_interventions_corrected'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPaneCount', @value=1 , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'linkagesbook_interventions_corrected'
GO
/****** Object:  View [dbo].[nanda_codes_2005]    Script Date: 11/21/2010 22:23:09 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[nanda_codes_2005]
AS
SELECT     dbo.nanda_codes_current.id, ISNULL(dbo.nanda_codes_current.name, dbo.nanda_codes_2005_to_current_mappings.new_name) AS name, 
                      dbo.nanda_codes_current.code
FROM         dbo.nanda_codes_current LEFT OUTER JOIN
                      dbo.nanda_codes_2005_to_current_mappings ON 
                      dbo.nanda_codes_current.code = dbo.nanda_codes_2005_to_current_mappings.new_nanda_code
WHERE     (dbo.nanda_codes_current.name NOT IN
                          (SELECT     name
                            FROM          dbo.nanda_codes_retired))
GO
EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPane1', @value=N'[0E232FF0-B466-11cf-A24F-00AA00A3EFFF, 1.00]
Begin DesignProperties = 
   Begin PaneConfigurations = 
      Begin PaneConfiguration = 0
         NumPanes = 4
         Configuration = "(H (1[40] 4[20] 2[20] 3) )"
      End
      Begin PaneConfiguration = 1
         NumPanes = 3
         Configuration = "(H (1 [50] 4 [25] 3))"
      End
      Begin PaneConfiguration = 2
         NumPanes = 3
         Configuration = "(H (1[50] 2[25] 3) )"
      End
      Begin PaneConfiguration = 3
         NumPanes = 3
         Configuration = "(H (4 [30] 2 [40] 3))"
      End
      Begin PaneConfiguration = 4
         NumPanes = 2
         Configuration = "(H (1 [56] 3))"
      End
      Begin PaneConfiguration = 5
         NumPanes = 2
         Configuration = "(H (2 [66] 3))"
      End
      Begin PaneConfiguration = 6
         NumPanes = 2
         Configuration = "(H (4 [50] 3))"
      End
      Begin PaneConfiguration = 7
         NumPanes = 1
         Configuration = "(V (3))"
      End
      Begin PaneConfiguration = 8
         NumPanes = 3
         Configuration = "(H (1[56] 4[18] 2) )"
      End
      Begin PaneConfiguration = 9
         NumPanes = 2
         Configuration = "(H (1 [75] 4))"
      End
      Begin PaneConfiguration = 10
         NumPanes = 2
         Configuration = "(H (1[66] 2) )"
      End
      Begin PaneConfiguration = 11
         NumPanes = 2
         Configuration = "(H (4 [60] 2))"
      End
      Begin PaneConfiguration = 12
         NumPanes = 1
         Configuration = "(H (1) )"
      End
      Begin PaneConfiguration = 13
         NumPanes = 1
         Configuration = "(V (4))"
      End
      Begin PaneConfiguration = 14
         NumPanes = 1
         Configuration = "(V (2))"
      End
      ActivePaneConfig = 2
   End
   Begin DiagramPane = 
      Begin Origin = 
         Top = 0
         Left = 0
      End
      Begin Tables = 
         Begin Table = "nanda_codes_current"
            Begin Extent = 
               Top = 14
               Left = 96
               Bottom = 118
               Right = 295
            End
            DisplayFlags = 280
            TopColumn = 0
         End
         Begin Table = "nanda_codes_2005_to_current_mappings"
            Begin Extent = 
               Top = 34
               Left = 353
               Bottom = 168
               Right = 625
            End
            DisplayFlags = 280
            TopColumn = 0
         End
      End
   End
   Begin SQLPane = 
   End
   Begin DataPane = 
      Begin ParameterDefaults = ""
      End
      Begin ColumnWidths = 9
         Width = 284
         Width = 1500
         Width = 2175
         Width = 4470
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
      End
   End
   Begin CriteriaPane = 
      PaneHidden = 
      Begin ColumnWidths = 11
         Column = 1440
         Alias = 900
         Table = 1170
         Output = 720
         Append = 1400
         NewValue = 1170
         SortType = 1350
         SortOrder = 1410
         GroupBy = 1350
         Filter = 1350
         Or = 1350
         Or = 1350
         Or = 1350
      End
   End
End
' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'nanda_codes_2005'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPaneCount', @value=1 , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'nanda_codes_2005'
GO
/****** Object:  View [dbo].[linkagesbook_diagnoses_corrected]    Script Date: 11/21/2010 22:23:09 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[linkagesbook_diagnoses_corrected]
AS
SELECT     dbo.linkagesbook_diagnoses.id, ISNULL(ISNULL(dbo.linkagesbook_diagnoses.nanda_code, 
                      dbo.nanda_codes_2005_to_current_mappings.new_nanda_code), dbo.nanda_codes_current.code) AS nanda_code, 
                      ISNULL(dbo.nanda_codes_2005_to_current_mappings.new_name, dbo.linkagesbook_diagnoses.name) AS name, 
                      dbo.linkagesbook_diagnoses.definition
FROM         dbo.linkagesbook_diagnoses LEFT OUTER JOIN
                      dbo.nanda_codes_current ON dbo.linkagesbook_diagnoses.name = dbo.nanda_codes_current.name LEFT OUTER JOIN
                      dbo.nanda_codes_2005_to_current_mappings ON 
                      dbo.linkagesbook_diagnoses.name = dbo.nanda_codes_2005_to_current_mappings.old_name
WHERE     (dbo.linkagesbook_diagnoses.name NOT IN
                          (SELECT     name
                            FROM          dbo.nanda_codes_retired))
GO
EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPane1', @value=N'[0E232FF0-B466-11cf-A24F-00AA00A3EFFF, 1.00]
Begin DesignProperties = 
   Begin PaneConfigurations = 
      Begin PaneConfiguration = 0
         NumPanes = 4
         Configuration = "(H (1[40] 4[20] 2[20] 3) )"
      End
      Begin PaneConfiguration = 1
         NumPanes = 3
         Configuration = "(H (1 [50] 4 [25] 3))"
      End
      Begin PaneConfiguration = 2
         NumPanes = 3
         Configuration = "(H (1[42] 2[31] 3) )"
      End
      Begin PaneConfiguration = 3
         NumPanes = 3
         Configuration = "(H (4 [30] 2 [40] 3))"
      End
      Begin PaneConfiguration = 4
         NumPanes = 2
         Configuration = "(H (1 [56] 3))"
      End
      Begin PaneConfiguration = 5
         NumPanes = 2
         Configuration = "(H (2 [66] 3))"
      End
      Begin PaneConfiguration = 6
         NumPanes = 2
         Configuration = "(H (4 [50] 3))"
      End
      Begin PaneConfiguration = 7
         NumPanes = 1
         Configuration = "(V (3))"
      End
      Begin PaneConfiguration = 8
         NumPanes = 3
         Configuration = "(H (1[56] 4[18] 2) )"
      End
      Begin PaneConfiguration = 9
         NumPanes = 2
         Configuration = "(H (1 [75] 4))"
      End
      Begin PaneConfiguration = 10
         NumPanes = 2
         Configuration = "(H (1[66] 2) )"
      End
      Begin PaneConfiguration = 11
         NumPanes = 2
         Configuration = "(H (4 [60] 2))"
      End
      Begin PaneConfiguration = 12
         NumPanes = 1
         Configuration = "(H (1) )"
      End
      Begin PaneConfiguration = 13
         NumPanes = 1
         Configuration = "(V (4))"
      End
      Begin PaneConfiguration = 14
         NumPanes = 1
         Configuration = "(V (2))"
      End
      ActivePaneConfig = 2
   End
   Begin DiagramPane = 
      Begin Origin = 
         Top = 0
         Left = 0
      End
      Begin Tables = 
         Begin Table = "linkagesbook_diagnoses"
            Begin Extent = 
               Top = 5
               Left = 24
               Bottom = 212
               Right = 242
            End
            DisplayFlags = 280
            TopColumn = 0
         End
         Begin Table = "nanda_codes_current"
            Begin Extent = 
               Top = 65
               Left = 565
               Bottom = 211
               Right = 802
            End
            DisplayFlags = 280
            TopColumn = 0
         End
         Begin Table = "nanda_codes_2005_to_current_mappings"
            Begin Extent = 
               Top = 73
               Left = 286
               Bottom = 190
               Right = 451
            End
            DisplayFlags = 280
            TopColumn = 0
         End
      End
   End
   Begin SQLPane = 
   End
   Begin DataPane = 
      Begin ParameterDefaults = ""
      End
      Begin ColumnWidths = 9
         Width = 284
         Width = 1500
         Width = 2730
         Width = 6705
         Width = 1995
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
      End
   End
   Begin CriteriaPane = 
      PaneHidden = 
      Begin ColumnWidths = 11
         Column = 1440
         Alias = 900
         Table = 1170
         Output = 720
         Append = 1400
         NewValue = 1170
         SortType = 1350
         SortOrder = 1410
         GroupBy = 1350
         Filter = 1350
         Or = 1350
         Or = 1350
         Or = 1350
      End
   End
End
' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'linkagesbook_diagnoses_corrected'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPaneCount', @value=1 , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'linkagesbook_diagnoses_corrected'
GO
/****** Object:  View [dbo].[linkagesbook_outcomes_corrected]    Script Date: 11/21/2010 22:23:09 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[linkagesbook_outcomes_corrected]
AS
SELECT     dbo.linkagesbook_outcomes.id, dbo.linkagesbook_outcomes.diagnosis_id, dbo.linkagesbook_outcomes.name, dbo.linkagesbook_outcomes.definition, 
                      dbo.noc_codes_2005.code AS noc_code
FROM         dbo.linkagesbook_outcomes LEFT OUTER JOIN
                      dbo.noc_codes_2005 ON dbo.linkagesbook_outcomes.noc_code_exception = dbo.noc_codes_2005.code OR 
                      dbo.linkagesbook_outcomes.name = dbo.noc_codes_2005.name
GO
EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPane1', @value=N'[0E232FF0-B466-11cf-A24F-00AA00A3EFFF, 1.00]
Begin DesignProperties = 
   Begin PaneConfigurations = 
      Begin PaneConfiguration = 0
         NumPanes = 4
         Configuration = "(H (1[40] 4[20] 2[20] 3) )"
      End
      Begin PaneConfiguration = 1
         NumPanes = 3
         Configuration = "(H (1 [50] 4 [25] 3))"
      End
      Begin PaneConfiguration = 2
         NumPanes = 3
         Configuration = "(H (1 [50] 2 [25] 3))"
      End
      Begin PaneConfiguration = 3
         NumPanes = 3
         Configuration = "(H (4 [30] 2 [40] 3))"
      End
      Begin PaneConfiguration = 4
         NumPanes = 2
         Configuration = "(H (1 [56] 3))"
      End
      Begin PaneConfiguration = 5
         NumPanes = 2
         Configuration = "(H (2 [66] 3))"
      End
      Begin PaneConfiguration = 6
         NumPanes = 2
         Configuration = "(H (4 [50] 3))"
      End
      Begin PaneConfiguration = 7
         NumPanes = 1
         Configuration = "(V (3))"
      End
      Begin PaneConfiguration = 8
         NumPanes = 3
         Configuration = "(H (1[56] 4[18] 2) )"
      End
      Begin PaneConfiguration = 9
         NumPanes = 2
         Configuration = "(H (1 [75] 4))"
      End
      Begin PaneConfiguration = 10
         NumPanes = 2
         Configuration = "(H (1[66] 2) )"
      End
      Begin PaneConfiguration = 11
         NumPanes = 2
         Configuration = "(H (4 [60] 2))"
      End
      Begin PaneConfiguration = 12
         NumPanes = 1
         Configuration = "(H (1) )"
      End
      Begin PaneConfiguration = 13
         NumPanes = 1
         Configuration = "(V (4))"
      End
      Begin PaneConfiguration = 14
         NumPanes = 1
         Configuration = "(V (2))"
      End
      ActivePaneConfig = 0
   End
   Begin DiagramPane = 
      Begin Origin = 
         Top = 0
         Left = 0
      End
      Begin Tables = 
         Begin Table = "linkagesbook_outcomes"
            Begin Extent = 
               Top = 6
               Left = 38
               Bottom = 162
               Right = 217
            End
            DisplayFlags = 280
            TopColumn = 0
         End
         Begin Table = "noc_codes_2005"
            Begin Extent = 
               Top = 15
               Left = 386
               Bottom = 123
               Right = 537
            End
            DisplayFlags = 280
            TopColumn = 0
         End
      End
   End
   Begin SQLPane = 
   End
   Begin DataPane = 
      Begin ParameterDefaults = ""
      End
      Begin ColumnWidths = 9
         Width = 284
         Width = 1500
         Width = 1500
         Width = 2580
         Width = 2175
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
      End
   End
   Begin CriteriaPane = 
      Begin ColumnWidths = 11
         Column = 1440
         Alias = 900
         Table = 1170
         Output = 720
         Append = 1400
         NewValue = 1170
         SortType = 1350
         SortOrder = 1410
         GroupBy = 1350
         Filter = 1350
         Or = 1350
         Or = 1350
         Or = 1350
      End
   End
End
' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'linkagesbook_outcomes_corrected'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPaneCount', @value=1 , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'linkagesbook_outcomes_corrected'
GO
/****** Object:  View [dbo].[correlations_between_diagnoses]    Script Date: 11/21/2010 22:23:09 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[correlations_between_diagnoses] as
select TOP 100 * from (
SELECT     TOP (100) PERCENT dbo.diagnoses.id AS diagnosis_id_a, diagnoses_1.id AS diagnosis_id_b, 
                      dbo.two_item_correlations.likelihood_ratio AS correlation
FROM         dbo.two_item_correlations INNER JOIN
                      dbo.two_item_correlation_diagnoses ON dbo.two_item_correlations.first_id = dbo.two_item_correlation_diagnoses.id INNER JOIN
                      dbo.two_item_correlation_diagnoses AS two_item_correlation_diagnoses_1 ON 
                      dbo.two_item_correlations.second_id = two_item_correlation_diagnoses_1.id INNER JOIN
                      dbo.diagnoses ON dbo.two_item_correlation_diagnoses.nanda_code = dbo.diagnoses.nanda_code INNER JOIN
                      dbo.diagnoses AS diagnoses_1 ON two_item_correlation_diagnoses_1.nanda_code = diagnoses_1.nanda_code
UNION 

SELECT     TOP (100) PERCENT diagnoses_1.id AS diagnosis_id_a, dbo.diagnoses.id AS diagnosis_id_b, 
                      dbo.two_item_correlations.likelihood_ratio AS correlation
FROM         dbo.two_item_correlations INNER JOIN
                      dbo.two_item_correlation_diagnoses ON dbo.two_item_correlations.first_id = dbo.two_item_correlation_diagnoses.id INNER JOIN
                      dbo.two_item_correlation_diagnoses AS two_item_correlation_diagnoses_1 ON 
                      dbo.two_item_correlations.second_id = two_item_correlation_diagnoses_1.id INNER JOIN
                      dbo.diagnoses ON dbo.two_item_correlation_diagnoses.nanda_code = dbo.diagnoses.nanda_code INNER JOIN
                      dbo.diagnoses AS diagnoses_1 ON two_item_correlation_diagnoses_1.nanda_code = diagnoses_1.nanda_code) correlations 
                      
WHERE correlation > 0 
order by diagnosis_id_a, correlation DESC
GO
/****** Object:  View [dbo].[linkages_book_interventions_needing_manual_matching]    Script Date: 11/21/2010 22:23:09 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[linkages_book_interventions_needing_manual_matching]
AS
SELECT     TOP (10000) dbo.linkagesbook_interventions_corrected.id, dbo.linkagesbook_interventions_corrected.outcome_id, 
                      dbo.linkagesbook_interventions_corrected.name, dbo.linkagesbook_interventions_corrected.type, dbo.linkagesbook_interventions_corrected.nic_code, 
                      dbo.linkagesbook_interventions_corrected.definition, dbo.linkagesbook_diagnoses_corrected.name AS diagnosis, 
                      dbo.linkagesbook_outcomes_corrected.name AS outcome
FROM         dbo.linkagesbook_interventions_corrected INNER JOIN
                      dbo.linkagesbook_outcomes_corrected ON 
                      dbo.linkagesbook_interventions_corrected.outcome_id = dbo.linkagesbook_outcomes_corrected.id INNER JOIN
                      dbo.linkagesbook_diagnoses_corrected ON dbo.linkagesbook_outcomes_corrected.diagnosis_id = dbo.linkagesbook_diagnoses_corrected.id
WHERE     (dbo.linkagesbook_interventions_corrected.nic_code IS NULL)
ORDER BY dbo.linkagesbook_interventions_corrected.name
GO
EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPane1', @value=N'[0E232FF0-B466-11cf-A24F-00AA00A3EFFF, 1.00]
Begin DesignProperties = 
   Begin PaneConfigurations = 
      Begin PaneConfiguration = 0
         NumPanes = 4
         Configuration = "(H (1[40] 4[20] 2[20] 3) )"
      End
      Begin PaneConfiguration = 1
         NumPanes = 3
         Configuration = "(H (1 [50] 4 [25] 3))"
      End
      Begin PaneConfiguration = 2
         NumPanes = 3
         Configuration = "(H (1 [50] 2 [25] 3))"
      End
      Begin PaneConfiguration = 3
         NumPanes = 3
         Configuration = "(H (4 [30] 2 [40] 3))"
      End
      Begin PaneConfiguration = 4
         NumPanes = 2
         Configuration = "(H (1 [56] 3))"
      End
      Begin PaneConfiguration = 5
         NumPanes = 2
         Configuration = "(H (2 [66] 3))"
      End
      Begin PaneConfiguration = 6
         NumPanes = 2
         Configuration = "(H (4 [50] 3))"
      End
      Begin PaneConfiguration = 7
         NumPanes = 1
         Configuration = "(V (3))"
      End
      Begin PaneConfiguration = 8
         NumPanes = 3
         Configuration = "(H (1[56] 4[18] 2) )"
      End
      Begin PaneConfiguration = 9
         NumPanes = 2
         Configuration = "(H (1 [75] 4))"
      End
      Begin PaneConfiguration = 10
         NumPanes = 2
         Configuration = "(H (1[66] 2) )"
      End
      Begin PaneConfiguration = 11
         NumPanes = 2
         Configuration = "(H (4 [60] 2))"
      End
      Begin PaneConfiguration = 12
         NumPanes = 1
         Configuration = "(H (1) )"
      End
      Begin PaneConfiguration = 13
         NumPanes = 1
         Configuration = "(V (4))"
      End
      Begin PaneConfiguration = 14
         NumPanes = 1
         Configuration = "(V (2))"
      End
      ActivePaneConfig = 0
   End
   Begin DiagramPane = 
      Begin Origin = 
         Top = 0
         Left = 0
      End
      Begin Tables = 
         Begin Table = "linkagesbook_interventions_corrected"
            Begin Extent = 
               Top = 6
               Left = 38
               Bottom = 114
               Right = 189
            End
            DisplayFlags = 280
            TopColumn = 0
         End
         Begin Table = "linkagesbook_outcomes_corrected"
            Begin Extent = 
               Top = 6
               Left = 217
               Bottom = 114
               Right = 378
            End
            DisplayFlags = 280
            TopColumn = 0
         End
         Begin Table = "linkagesbook_diagnoses_corrected"
            Begin Extent = 
               Top = 6
               Left = 416
               Bottom = 114
               Right = 567
            End
            DisplayFlags = 280
            TopColumn = 0
         End
      End
   End
   Begin SQLPane = 
   End
   Begin DataPane = 
      Begin ParameterDefaults = ""
      End
   End
   Begin CriteriaPane = 
      Begin ColumnWidths = 11
         Column = 1440
         Alias = 900
         Table = 1170
         Output = 720
         Append = 1400
         NewValue = 1170
         SortType = 1350
         SortOrder = 1410
         GroupBy = 1350
         Filter = 1350
         Or = 1350
         Or = 1350
         Or = 1350
      End
   End
End
' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'linkages_book_interventions_needing_manual_matching'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPaneCount', @value=1 , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'linkages_book_interventions_needing_manual_matching'
GO
/****** Object:  ForeignKey [FK_diagnosis_outcomes_diagnoses]    Script Date: 11/21/2010 22:23:08 ******/
ALTER TABLE [dbo].[diagnosis_outcomes]  WITH CHECK ADD  CONSTRAINT [FK_diagnosis_outcomes_diagnoses] FOREIGN KEY([diagnosis_id])
REFERENCES [dbo].[diagnoses] ([id])
GO
ALTER TABLE [dbo].[diagnosis_outcomes] CHECK CONSTRAINT [FK_diagnosis_outcomes_diagnoses]
GO
/****** Object:  ForeignKey [FK_diagnosis_outcomes_outcomes]    Script Date: 11/21/2010 22:23:08 ******/
ALTER TABLE [dbo].[diagnosis_outcomes]  WITH CHECK ADD  CONSTRAINT [FK_diagnosis_outcomes_outcomes] FOREIGN KEY([outcome_id])
REFERENCES [dbo].[outcomes] ([id])
GO
ALTER TABLE [dbo].[diagnosis_outcomes] CHECK CONSTRAINT [FK_diagnosis_outcomes_outcomes]
GO
/****** Object:  ForeignKey [FK_linkagesbook_outcomes_linkagesbook_diagnoses]    Script Date: 11/21/2010 22:23:08 ******/
ALTER TABLE [dbo].[linkagesbook_outcomes]  WITH CHECK ADD  CONSTRAINT [FK_linkagesbook_outcomes_linkagesbook_diagnoses] FOREIGN KEY([diagnosis_id])
REFERENCES [dbo].[linkagesbook_diagnoses] ([id])
GO
ALTER TABLE [dbo].[linkagesbook_outcomes] CHECK CONSTRAINT [FK_linkagesbook_outcomes_linkagesbook_diagnoses]
GO
/****** Object:  ForeignKey [FK_linkagesbook_interventions_linkagesbook_outcomes]    Script Date: 11/21/2010 22:23:08 ******/
ALTER TABLE [dbo].[linkagesbook_interventions]  WITH CHECK ADD  CONSTRAINT [FK_linkagesbook_interventions_linkagesbook_outcomes] FOREIGN KEY([outcome_id])
REFERENCES [dbo].[linkagesbook_outcomes] ([id])
GO
ALTER TABLE [dbo].[linkagesbook_interventions] CHECK CONSTRAINT [FK_linkagesbook_interventions_linkagesbook_outcomes]
GO

