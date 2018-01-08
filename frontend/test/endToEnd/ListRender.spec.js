describe('List', () => {
  it('renders', async () => {
    const page = await browser.newPage();
    await page.goto('http://localhost:3000/');

    const LIST_ITEM_SELECTOR = '[role="list"] li';
    await page.waitFor(LIST_ITEM_SELECTOR);

    const innerText = await page.evaluate(sel => {
      return document.querySelector(sel).innerText;
    }, LIST_ITEM_SELECTOR);

    expect(innerText).to.be.equal('Dot Matrix');
  });
});
