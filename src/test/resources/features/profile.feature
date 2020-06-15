@profile
Feature: Profile

  Scenario: open profile enlarge every menu point and verify the header
    Given I am on home page
    When I click on profile tab
    Then profile page should get open
    And I should see different menus available on profile page
    Then I click on each menu and verify content
 