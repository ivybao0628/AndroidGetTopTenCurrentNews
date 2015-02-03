# AndroidGetTopTenCurrentNews
Highlight
==========================================================================
1) Get the 10 latest news from USAToday's API.
    User can pick the section that he/she is interested in.
2) Get a thumbnail from Bing Search V1
    Query is based on the news result from Feature 1
3) Randomly stack NewsFragment's linearLayout.



Key word:
Asynctask, PageView, Fragment, Singleton, Splash screen, SharePreference, FragmentPageViewAdapter
USAToday API, BING Search V1 API, HTTP request

==========================================================================
Here is the list of function that are still missing.
    - Connect Auto Swipe
    - Block back
    - Action Bar add setting go to page 0
    - Clean all data when search button is clicked for the second time.

    - Make link clickable.
        resolve with intent
    - Fix swipe
        There is a leak.

    - Error Checking EX. Connectivity, WebService down
    - Check network during splash screen
        error then .finish if internet is not available.

@Better design pattern
@Better variable name

==========================================================================