package net.darktree.glslmc.settings;

import net.darktree.glslmc.PanoramaClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.ButtonListWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

public class ShaderSettingsScreen extends Screen {

	private static final Text NOTE = new TranslatableText("screen.glsl_panorama.note").formatted(Formatting.GRAY);
	private static final Text RELOAD = new TranslatableText("screen.glsl_panorama.reload");
	private final Screen parent;

	public ShaderSettingsScreen() {
		super(new TranslatableText("screen.glsl_panorama.title"));
		parent = MinecraftClient.getInstance().currentScreen;
	}

	@Override
	protected void init() {
		ButtonListWidget options = new ButtonListWidget(this.client, this.width, this.height, 32, this.height - 32, 25);
		options.addOptionEntry(Options.get().ENABLED, Options.get().QUALITY);

		this.addDrawableChild(options);

		this.addDrawableChild(new ButtonWidget(this.width / 2 + 4, this.height - 27, 150, 20, ScreenTexts.DONE, button -> this.onClose()));
		this.addDrawableChild(new ButtonWidget(this.width / 2 - 154, this.height - 27, 150, 20, RELOAD, button -> {
			client.reloadResources();
		}));
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		this.renderBackground(matrices);
		super.render(matrices, mouseX, mouseY, delta);
		DrawableHelper.drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 5, 0xFFFFFF);
		DrawableHelper.drawCenteredText(matrices, this.textRenderer, NOTE, this.width / 2, 20, 0xFFFFFF);
	}

	@Override
	public void removed() {
		Options.get().save();
	}

	@Override
	public boolean shouldCloseOnEsc() {
		return true;
	}

	@Override
	public void onClose() {
		this.client.setScreen(this.parent);
	}

}
