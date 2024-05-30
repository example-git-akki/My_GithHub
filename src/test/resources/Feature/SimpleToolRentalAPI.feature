Feature: To Automate and Verify the Response of Simple Tool Rental API Document.

  @GET_STATUS
  Scenario: To Verify the Get HTTP Request for the API call GET_STATUS
    When user calls the GET_STATUS API call using GET HTTP REQUEST
    Then user verifies the STATUS of GET_STATUS API call as 200
    Then user extracts the value of the STATUS from the GET_STATUS API Response

  @GET_TOOLS
  Scenario: To verify the GET HTTP REQUEST for the API call GET_TOOLS
    When user calls the GET_TOOLS API call using GET HTTP Request
    Then user verifies the STATUS of GET_STATUS API call as 200
    Then user extracts the details from the GET_TOOLS API call

  @GET_SINGLE_TOOL
  Scenario Outline: To verify that a particular tool is obtained for the API call GET_SINGLE_TOOLS
    When user calls the GET_SINGLE_TOOL API call using GET HTTP Request adding path Parameter "<toolId>" toolID
    Then user verifies the STATUS of GET_STATUS API call as 200
    Then user extracts the details from the GET_SINGLE_TOOL API call
    Then user validates the id extracted with the toolId "<toolId>"
    Examples:
      | toolId |
      | 6543   |


  @AUTHENTICATION
  Scenario Outline: To Verify that the access Token is generated once the POST HTTP REQUEST is called
    When user calls the POST HTTP REQUEST for the AUTHENTICATION REQUEST with "<clientName>" and "<clientEmail>" as BODY
    Then user extracts the ACCESS TOKEN generated after POST HTTP REQUEST
    Examples:
      | clientName | clientEmail           |
      | Postman    | akpatil15@example.com |


  @getOrders
  Scenario: To Verify the orders once the GET_ORDER HTTP REQUEST is made
    When user calls GET_ORDERS HTTP REQUEST with ACCESS Token for AUTHORIZATION
    Then user verifies the STATUS of GET_Orders API call as 200

  @Create_New_Order
  Scenario Outline:To verify that user has created an order with POST HTTP REQUEST
    When user calls CREATE_NEW_ORDER HTTP POST REQUEST with accessToken And Body having "<toolId>" and "<customerName>"
    Then user verifies the STATUS of CREATE_NEW_ORDER API call as 201
    Then user extracts the order-Id
    Examples:
      | toolId | customerName |
      | 3674   | John Doe     |