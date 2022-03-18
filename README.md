Encoding Refactoring Kata
---------------------------

The tests work but could be better structured. 

If you introduce a bug - for example by changing a 0x1F to a 0x1E in XyzTimer - then the test should fail. That's great, you want your tests to find bugs! However, you also want your tests to make it really easy to understand what's gone wrong. How many lines of code will you have to read before you grasp what's being tested? Will the name of the test help you?

If you refactor this test to use patterns like a Test Fixture and an Arrange-Act-Assert structure, you should be able to reduce the amount of code you need to read in order to understand a test failure. Perhaps it's not a huge difference, but across a big test suite, it could significantly reduce your test maintenance costs.

## New test
Add a test which has an XyzTimer using MultiplesOfSeconds, like this:

    command.setXyzTimer(MultiplesOfSeconds, 30)

Add an assertion for the encoded value. 