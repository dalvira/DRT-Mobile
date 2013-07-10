DRT-Mobile by Team Universe
==========

Android Coding Challenge

This project is per the design requirements provided in "DRT-Mobile/Design Documents/".

Any issues should be reported to our team leader, Darius Alvira, at da868j@att.com

Thanks for using our creation! <3

----------------------------------------------------------------------------

How the classes correspond to business requirements:

	LogonActivity.java		: BR-1.A

	IncidentSearchFragment.java	: BR-1.B, BR-1.E

	IncidentResultsFragment.java	: BR-1.C

	BuildingSearchFragment		: BR-1.D, BR-1.E

	BuildingResultsFragment.java	: BR-1.D

	DamageAssessmentFragment.java	: BR-2.A, BR-1.F

	DamageGetFragment.java		: BR-2.A

	DamageAddFragment.java		: BR-1.F

	DamageUpdateFragment.java	: BR-2.A

	ReportSelectionFragment.java	: BR-3.A


How business requirements correspond to classes:

	BR-1.A : LogonActivity.java

	BR-1.B : IncidentSearchFragment.java

	BR-1.C : IncidentResultsFragment.java

	BR-1.D : BuildingSearchFragment.java, BuildingResultsFragment.java

	BR-1.E : IncidentSearchFragment.java, BuildingSearchFragment.java

	BR-1.F : DamageAssessmentFragment.java, DamageAddFragment.java

	BR-2.A : DamageAssessmentFragment.java, DamageGetFragment.java, DamageUpdateFragment.java

	BR-3.A : ReportSelectionFragment.java


How the classes correspond to system requirements:

	LogonActivity.java		: SR-1.A

	IncidentSearchFragment.java	: SR-1.F

	IncidentResultsFragment.java	: SR-1.F

	BuildingSearchFragment		: SR-1.F

	BuildingResultsFragment.java	: SR-1.F

	DamageAssessmentFragment.java	: SR-1.E, SR-2.A

	DamageGetFragment.java		: SR-2.A

	DamageAddFragment.java		: SR-1.E

	DamageUpdateFragment.java	: SR-2.A

	ReportSelectionFragment.java	: SR-3.A, SR-3.B, SR-3.C


How system requirements correspond to classes:

	SR-1.A : LogonActivity.java

	SR-1.B : Merged into SR-1.F

	SR-1.C : Merged into SR-1.F

	SR-1.D : Merged into SR-1.F

	SR-1.E : DamageAddFragment.java

	SR-1.F : IncidentSearchFragment.java, IncidentResultsFragment.java, BuildingSearchFragment.java, BuildingResultsFragment.java

	SR-2.A : DamageGetFragment.java, DamageUpdateFragment.java

	SR-3.A : ReportSelection.java

	SR-3.B : Will link from ReportSelection.java

	SR-3.C : Will link from ReportSelection.java