package com.backstage.check;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class ObjectBlockLeftCurlyCheck extends AbstractCheck
{
	@Override
	public int[] getDefaultTokens()
	{
		return new int[] {
				TokenTypes.OBJBLOCK
		};
	}

	@Override
	public int[] getAcceptableTokens()
	{
		return new int[] {
				TokenTypes.OBJBLOCK
		};
	}

	@Override
	public int[] getRequiredTokens()
	{
		return CommonUtil.EMPTY_INT_ARRAY;
	}

	@Override
	public void visitToken(DetailAST ast)
	{
		var parentToken = ast.getParent();
		var brace = ast.findFirstToken(TokenTypes.LCURLY);

		if (brace == null)
		{
			return;
		}

		boolean isAnonymousClassToken = parentToken.getType() == TokenTypes.LITERAL_NEW;
		var braceLine = getLine(brace.getLineNo() - 1);

		if (isAnonymousClassToken)
		{
			if (CommonUtil.hasWhitespaceBefore(brace.getColumnNo(), braceLine))
			{
				log(brace, "'{' Ожидалась на той же строке");
			}
		}
		else
		{
			if (!CommonUtil.hasWhitespaceBefore(brace.getColumnNo(), braceLine))
			{
				log(brace, "'{' Ожидалась на новой строке");
			}
		}
	}
}
