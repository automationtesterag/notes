```Javascript
import { browser, $, $$, expect } from '@wdio/globals';

describe('WebdriverIO - Common Actions', () => {

  it('should perform common WDIO actions', async () => {

    // ============================================================
    // 1. NAVIGATION
    // ============================================================

    await browser.url('https://example.com');

    await browser.refresh();
    await browser.back();
    await browser.forward();

    console.log(await browser.getUrl());
    console.log(await browser.getTitle());


    // ============================================================
    // 2. LOCATORS
    // ============================================================

    const username = await $('#username');                    // ID
    const password = await $('.password');                   // CSS
    const loginBtn = await $('//button[@type="submit"]');    // XPath
    const submit = await $('button=Submit');                 // Text
    const buttons = await $$('button');                      // Multiple


    // ============================================================
    // 3. ELEMENT STATE
    // ============================================================

    console.log(await username.isDisplayed());
    console.log(await username.isEnabled());
    console.log(await username.isSelected());
    console.log(await username.isExisting());


    // ============================================================
    // 4. CLICK ACTIONS
    // ============================================================

    await loginBtn.click();
    await loginBtn.doubleClick();
    await loginBtn.click({ button: 'right' });


    // ============================================================
    // 5. TEXT / INPUT ACTIONS
    // ============================================================

    await username.setValue('Anudeep');
    await username.addValue(' Test');
    await username.clearValue();

    console.log(await username.getText());
    console.log(await username.getValue());


    // ============================================================
    // 6. ATTRIBUTE / PROPERTY
    // ============================================================

    console.log(await username.getAttribute('placeholder'));
    console.log(await username.getAttribute('value'));
    console.log(await username.getProperty('value'));


    // ============================================================
    // 7. WAIT ACTIONS
    // ============================================================

    await username.waitForExist();
    await username.waitForDisplayed();
    await username.waitForEnabled();
    await username.waitForClickable();

    await username.waitUntil(
      async () => (await username.getText()) === 'Success',
      {
        timeout: 5000,
        timeoutMsg: 'Expected text not displayed'
      }
    );


    // ============================================================
    // 8. DROPDOWN
    // ============================================================

    const country = await $('#country');

    await country.selectByVisibleText('India');
    await country.selectByAttribute('value', 'IN');
    await country.selectByIndex(2);


    // ============================================================
    // 9. CHECKBOX / RADIO
    // ============================================================

    await $('#terms').click();
    console.log(await $('#terms').isSelected());

    await $('#male').click();
    console.log(await $('#male').isSelected());


    // ============================================================
    // 10. MOUSE ACTIONS
    // ============================================================

    await username.moveTo();
    await username.click();
    await username.doubleClick();

    await $('#source').dragAndDrop($('#target'));


    // ============================================================
    // 11. SCROLL
    // ============================================================

    await username.scrollIntoView();

    await browser.execute(() => {
      window.scrollTo(0, document.body.scrollHeight);
    });

    await browser.execute(() => {
      window.scrollTo(0, 0);
    });


    // ============================================================
    // 12. JAVASCRIPT
    // ============================================================

    const title = await browser.execute(() => {
      return document.title;
    });

    console.log(title);


    // ============================================================
    // 13. KEYBOARD
    // ============================================================

    await browser.keys('Enter');
    await browser.keys('Tab');
    await browser.keys('Escape');
    await browser.keys('ArrowDown');

    await browser.keys(['Control', 'a']);
    await browser.keys(['Control', 'c']);
    await browser.keys(['Control', 'v']);


    // ============================================================
    // 14. ALERT
    // ============================================================

    console.log(await browser.getAlertText());

    await browser.acceptAlert();
    await browser.dismissAlert();

    await browser.sendAlertText('Anudeep');


    // ============================================================
    // 15. IFRAME
    // ============================================================

    const frame = await $('#payment-frame');

    await frame.switchToFrame();

    await $('#cardNumber').setValue('4111111111111111');

    await browser.switchToParentFrame();

    // Default content
    await browser.switchToFrame(null);


    // ============================================================
    // 16. WINDOWS / TABS
    // ============================================================

    const currentWindow = await browser.getWindowHandle();

    await browser.newWindow('https://example.com');

    const windows = await browser.getWindowHandles();

    await browser.switchToWindow(windows[1]);

    console.log(await browser.getTitle());

    await browser.closeWindow();

    await browser.switchToWindow(currentWindow);


    // ============================================================
    // 17. COOKIES
    // ============================================================

    console.log(await browser.getCookies());

    await browser.setCookies({
      name: 'sessionId',
      value: '123456'
    });

    await browser.deleteCookies('sessionId');

    await browser.deleteCookies();


    // ============================================================
    // 18. LOCAL STORAGE
    // ============================================================

    await browser.execute(() => {
      localStorage.setItem('token', 'abc123');
    });

    console.log(
      await browser.execute(() =>
        localStorage.getItem('token')
      )
    );


    // ============================================================
    // 19. SESSION STORAGE
    // ============================================================

    await browser.execute(() => {
      sessionStorage.setItem('user', 'Anudeep');
    });

    console.log(
      await browser.execute(() =>
        sessionStorage.getItem('user')
      )
    );


    // ============================================================
    // 20. SCREENSHOT
    // ============================================================

    await browser.saveScreenshot('./screenshots/home.png');

    await username.saveScreenshot('./screenshots/username.png');


    // ============================================================
    // 21. WINDOW
    // ============================================================

    await browser.setWindowSize(1920, 1080);

    console.log(await browser.getWindowSize());

    await browser.fullscreen();


    // ============================================================
    // 22. ELEMENT COLLECTION
    // ============================================================

    const products = await $$('.product');

    console.log(await products.length);

    for (const product of products) {
      console.log(await product.getText());
    }


    // ============================================================
    // 23. CHILD ELEMENT
    // ============================================================

    const card = await $('.product-card');

    const cardTitle = await card.$('.title');

    console.log(await cardTitle.getText());


    // ============================================================
    // 24. FILE UPLOAD
    // ============================================================

    const file = await $('input[type="file"]');

    await file.setValue('/path/to/file.pdf');


    // ============================================================
    // 25. ASSERTIONS
    // ============================================================

    await expect(username).toBeDisplayed();

    await expect(loginBtn).toBeEnabled();

    await expect(username).toExist();

    await expect(loginBtn).toHaveText('Login');

    await expect(loginBtn).toHaveTextContaining('Log');

    await expect(username).toHaveValue('Anudeep');

    await expect(username).toHaveAttribute(
      'placeholder',
      'Username'
    );

    await expect(browser).toHaveUrlContaining('/dashboard');

    await expect(browser).toHaveTitle('Dashboard');


    // ============================================================
    // 26. PAUSE
    // ============================================================

    await browser.pause(2000);


    // ============================================================
    // 27. MOBILE / APPIUM
    // ============================================================

    await $('#login').click();

    await browser.swipe({
      direction: 'up',
      duration: 1000
    });

    await browser.back();

    console.log(await browser.getContext());

    console.log(await browser.getContexts());

    await browser.switchContext('WEBVIEW');

    await browser.switchContext('NATIVE_APP');

    await browser.hideKeyboard();


    // ============================================================
    // 28. COMPLETE LOGIN FLOW
    // ============================================================

    await browser.url('https://example.com/login');

    const user = await $('#username');
    const pass = await $('#password');
    const login = await $('button[type="submit"]');

    await user.waitForDisplayed();

    await user.setValue('admin');
    await pass.setValue('Password123');

    await expect(login).toBeEnabled();

    await login.click();

    await browser.waitUntil(
      async () => (await browser.getUrl()).includes('/dashboard'),
      {
        timeout: 10000,
        timeoutMsg: 'Dashboard was not loaded'
      }
    );

    await expect(browser).toHaveUrlContaining('/dashboard');

  });

});
```
